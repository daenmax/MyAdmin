package cn.daenx.system.service.impl;

import cn.daenx.framework.common.constant.SystemConstant;
import cn.daenx.framework.common.vo.system.config.SysSmsTemplateConfigVo;
import cn.daenx.framework.common.vo.system.other.SysJobVo;
import cn.daenx.framework.dataScope.annotation.DataScope;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.notify.dingTalk.utils.DingTalkUtil;
import cn.daenx.framework.notify.email.utils.EmailUtil;
import cn.daenx.framework.common.utils.MyUtil;
import cn.daenx.framework.notify.sms.utils.SmsUtil;
import cn.daenx.framework.common.vo.ComStatusUpdVo;
import cn.daenx.framework.quartz.constant.QuartzConstant;
import cn.daenx.framework.quartz.constant.ScheduleConstants;
import cn.daenx.framework.quartz.exception.TaskException;
import cn.daenx.framework.quartz.utils.CronUtils;
import cn.daenx.framework.quartz.utils.ScheduleUtils;
import cn.daenx.system.mapper.SysJobMapper;
import cn.daenx.system.domain.po.SysJob;
import cn.daenx.system.service.SysConfigService;
import cn.daenx.system.service.SysJobService;
import cn.daenx.system.domain.vo.SysJobAddVo;
import cn.daenx.system.domain.vo.SysJobPageVo;
import cn.daenx.system.domain.vo.SysJobUpdVo;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SysJobServiceImpl extends ServiceImpl<SysJobMapper, SysJob> implements SysJobService {
    @Resource
    private SysJobMapper sysJobMapper;
    @Resource
    private SysConfigService sysConfigService;
    @Resource
    private Scheduler scheduler;
    @Value("${system-info.name}")
    private String systemInfoName;

    /**
     * 初始化定时任务
     */
    @Override
    public void initJob() {
        try {
            scheduler.clear();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        List<SysJob> list = list();
        for (SysJob sysJob : list) {
            addSchedulerJob(sysJob);
        }
    }


    /**
     * 设置下次执行时间
     *
     * @param sysJob
     */
    private void setNextRunTime(SysJob sysJob) {
        Date nextExecution = CronUtils.getNextExecution(sysJob.getCronExpression());
        LocalDateTime localDateTime = MyUtil.toLocalDateTime(nextExecution.getTime());
        sysJob.setNextValidTime(localDateTime);
    }

    private LambdaQueryWrapper<SysJob> getWrapper(SysJobPageVo vo) {
        LambdaQueryWrapper<SysJob> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(ObjectUtil.isNotEmpty(vo.getJobName()), SysJob::getJobName, vo.getJobName());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getJobGroup()), SysJob::getJobGroup, vo.getJobGroup());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getInvokeTarget()), SysJob::getInvokeTarget, vo.getInvokeTarget());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getCronExpression()), SysJob::getCronExpression, vo.getCronExpression());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getStatus()), SysJob::getStatus, vo.getStatus());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getMisfirePolicy()), SysJob::getMisfirePolicy, vo.getMisfirePolicy());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getConcurrent()), SysJob::getConcurrent, vo.getConcurrent());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getRemark()), SysJob::getRemark, vo.getRemark());
        String startTime = vo.getStartTime();
        String endTime = vo.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), SysJob::getCreateTime, startTime, endTime);
        return wrapper;
    }

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @DataScope(alias = "sys_job")
    @Override
    public IPage<SysJob> getPage(SysJobPageVo vo) {
        LambdaQueryWrapper<SysJob> wrapper = getWrapper(vo);
        Page<SysJob> sysJobPage = sysJobMapper.selectPage(vo.getPage(true), wrapper);
        for (SysJob record : sysJobPage.getRecords()) {
            setNextRunTime(record);
        }
        return sysJobPage;
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @DataScope(alias = "sys_job")
    @Override
    public SysJob getInfo(String id) {
        SysJob sysJob = sysJobMapper.selectById(id);
        setNextRunTime(sysJob);
        return sysJob;
    }

    /**
     * 新增
     *
     * @param vo
     */
    @Override
    public void addInfo(SysJobAddVo vo) {
        if (!CronUtils.isValid(vo.getCronExpression())) {
            throw new MyException("新增任务失败，Cron表达式不正确");
        }
        if (StringUtils.containsIgnoreCase(vo.getInvokeTarget(), QuartzConstant.LOOKUP_RMI)) {
            throw new MyException("新增任务失败，目标字符串不允许'rmi'调用");
        }
        if (StringUtils.containsAnyIgnoreCase(vo.getInvokeTarget(), new String[]{QuartzConstant.LOOKUP_LDAP, QuartzConstant.LOOKUP_LDAPS})) {
            throw new MyException("新增任务失败，目标字符串不允许'ldap(s)'调用");
        }
        if (StringUtils.containsAnyIgnoreCase(vo.getInvokeTarget(), new String[]{"http://", "https://"})) {
            throw new MyException("新增任务失败，目标字符串不允许'http(s)'调用");
        }
        if (StringUtils.containsAnyIgnoreCase(vo.getInvokeTarget(), QuartzConstant.JOB_ERROR_STR)) {
            throw new MyException("新增任务失败，目标字符串存在违规");
        }
        if (!ScheduleUtils.whiteList(vo.getInvokeTarget())) {
            throw new MyException("新增任务失败，目标字符串不在白名单内");
        }
        SysJob sysJob = new SysJob();
        sysJob.setJobName(vo.getJobName());
        sysJob.setJobGroup(vo.getJobGroup());
        sysJob.setInvokeTarget(vo.getInvokeTarget());
        sysJob.setCronExpression(vo.getCronExpression());
        sysJob.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        sysJob.setMisfirePolicy(vo.getMisfirePolicy());
        sysJob.setConcurrent(vo.getConcurrent());
        sysJob.setRemark(vo.getRemark());
        sysJob.setNotifyChannel(vo.getNotifyChannel());
        sysJob.setNotifyObjs(vo.getNotifyObjs());
        int insert = sysJobMapper.insert(sysJob);
        if (insert < 1) {
            throw new MyException("新增失败");
        }

        SysJob job = getInfo(sysJob.getId());
        addSchedulerJob(job);

    }

    /**
     * 修改
     *
     * @param vo
     */
    @DataScope(alias = "sys_job")
    @Override
    public void editInfo(SysJobUpdVo vo) {
        if (!CronUtils.isValid(vo.getCronExpression())) {
            throw new MyException("修改任务失败，Cron表达式不正确");
        }
        if (StringUtils.containsIgnoreCase(vo.getInvokeTarget(), QuartzConstant.LOOKUP_RMI)) {
            throw new MyException("修改任务失败，目标字符串不允许'rmi'调用");
        }
        if (StringUtils.containsAnyIgnoreCase(vo.getInvokeTarget(), new String[]{QuartzConstant.LOOKUP_LDAP, QuartzConstant.LOOKUP_LDAPS})) {
            throw new MyException("修改任务失败，目标字符串不允许'ldap(s)'调用");
        }
        if (StringUtils.containsAnyIgnoreCase(vo.getInvokeTarget(), new String[]{"http://", "https://"})) {
            throw new MyException("修改任务失败，目标字符串不允许'http(s)'调用");
        }
        if (StringUtils.containsAnyIgnoreCase(vo.getInvokeTarget(), QuartzConstant.JOB_ERROR_STR)) {
            throw new MyException("修改任务失败，目标字符串存在违规");
        }
        if (!ScheduleUtils.whiteList(vo.getInvokeTarget())) {
            throw new MyException("修改任务失败，目标字符串不在白名单内");
        }

        LambdaUpdateWrapper<SysJob> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysJob::getId, vo.getId());
        updateWrapper.set(SysJob::getJobName, vo.getJobName());
        updateWrapper.set(SysJob::getJobGroup, vo.getJobGroup());
        updateWrapper.set(SysJob::getInvokeTarget, vo.getInvokeTarget());
        updateWrapper.set(SysJob::getCronExpression, vo.getCronExpression());
        updateWrapper.set(SysJob::getStatus, vo.getStatus());
        updateWrapper.set(SysJob::getMisfirePolicy, vo.getMisfirePolicy());
        updateWrapper.set(SysJob::getConcurrent, vo.getConcurrent());
        updateWrapper.set(SysJob::getRemark, vo.getRemark());
        updateWrapper.set(SysJob::getNotifyChannel, vo.getNotifyChannel());
        updateWrapper.set(SysJob::getNotifyObjs, vo.getNotifyObjs());
        int rows = sysJobMapper.update(new SysJob(), updateWrapper);
        if (rows < 1) {
            throw new MyException("修改失败");
        }
        SysJob job = getInfo(vo.getId());
        updateSchedulerJob(job);
    }

    /**
     * 修改状态
     *
     * @param vo
     */
    @DataScope(alias = "sys_job")
    @Override
    public void changeStatus(ComStatusUpdVo vo) {
        LambdaUpdateWrapper<SysJob> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysJob::getId, vo.getId());
        updateWrapper.set(SysJob::getStatus, vo.getStatus());
        int rows = sysJobMapper.update(new SysJob(), updateWrapper);
        if (rows < 1) {
            throw new MyException("修改失败");
        }
        SysJob job = getInfo(vo.getId());
        if (ScheduleConstants.Status.NORMAL.getValue().equals(vo.getStatus())) {
            resumeSchedulerJob(job);
        } else if (ScheduleConstants.Status.PAUSE.getValue().equals(vo.getStatus())) {
            pauseSchedulerJob(job);
        }
    }

    /**
     * 删除
     *
     * @param ids
     */
    @DataScope(alias = "sys_job")
    @Override
    public void deleteByIds(List<String> ids) {
        for (String id : ids) {
            SysJob job = getInfo(id);
            int i = sysJobMapper.deleteById(job);
            if (i > 0) {
                delSchedulerJob(job);
            }
        }
    }

    /**
     * 定时任务立即执行一次
     *
     * @param id
     */
    @Override
    public void run(String id) {
        SysJob job = getInfo(id);
        if (ObjectUtil.isEmpty(job)) {
            throw new MyException("定时任务不存在");
        }
        runSchedulerJob(job);
    }

    /**
     * 异常通知
     *
     * @param sysJob
     */
    @Async
    @EventListener
    public void sendNotify(SysJobVo sysJob) {
        if (ObjectUtil.isEmpty(sysJob.getNotifyObjs())) {
            log.info("没有填写通知对象，通知线程结束");
            return;
        }
        String errorMsg = sysJob.getErrorMsg();
        if (SystemConstant.NOTIFY_CHANNEL_NO.equals(sysJob.getNotifyChannel())) {
            //不通知
        } else if (SystemConstant.NOTIFY_CHANNEL_EMAIL.equals(sysJob.getNotifyChannel())) {
            //邮件
            String subject = "【" + systemInfoName + "】" + "[定时任务执行异常]" + sysJob.getJobName();
            String content = "异常信息：\n" + errorMsg;
            EmailUtil.sendEmail(sysJob.getNotifyObjs(), subject, content, false, null);
        } else if (SystemConstant.NOTIFY_CHANNEL_SMS.equals(sysJob.getNotifyChannel())) {
            //短信
            SysSmsTemplateConfigVo sysSmsTemplateConfigVo = sysConfigService.getSysSmsTemplateConfigVo();
            if (ObjectUtil.isEmpty(sysSmsTemplateConfigVo)) {
                log.info("没有配置短信模板参数，通知线程结束");
                return;
            }
            if (ObjectUtil.isEmpty(sysSmsTemplateConfigVo.getJobError())) {
                log.info("没有配置jobError短信模板参数，通知线程结束");
                return;
            }
            errorMsg = StringUtils.substring(errorMsg, 0, sysSmsTemplateConfigVo.getJobError().getLength());
            Map<String, String> smsMap = new HashMap<>();
            smsMap.put(sysSmsTemplateConfigVo.getJobError().getVariable(), errorMsg);
            SmsUtil.sendSms(sysJob.getNotifyObjs(), sysSmsTemplateConfigVo.getJobError().getTemplateId(), smsMap);
        } else if (SystemConstant.NOTIFY_CHANNEL_DING.equals(sysJob.getNotifyChannel())) {
            //钉钉
            String msg = "【" + systemInfoName + "】" + "[定时任务执行异常]\n任务名称：" + sysJob.getJobName() + "\n" + "异常信息：\n" + errorMsg;
            DingTalkUtil.sendMsg(sysJob.getNotifyObjs(), msg);
        }
    }

    /**
     * 添加任务
     *
     * @param job
     */
    public void addSchedulerJob(SysJob job) {
        try {
            SysJobVo sysJobVo = new SysJobVo();
            BeanUtils.copyProperties(job, sysJobVo);
            ScheduleUtils.createScheduleJob(scheduler, sysJobVo);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        } catch (TaskException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 更新任务
     *
     * @param job
     */
    public void updateSchedulerJob(SysJob job) {
        try {
            SysJobVo sysJobVo = new SysJobVo();
            BeanUtils.copyProperties(job, sysJobVo);
            // 判断是否存在
            JobKey jobKey = ScheduleUtils.getJobKey(job.getId(), job.getJobGroup());
            if (scheduler.checkExists(jobKey)) {
                // 防止创建时存在数据问题 先移除，然后在执行创建操作
                scheduler.deleteJob(jobKey);
            }
            ScheduleUtils.createScheduleJob(scheduler, sysJobVo);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        } catch (TaskException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 恢复任务
     *
     * @param job
     */
    private void resumeSchedulerJob(SysJob job) {
        try {
            scheduler.resumeJob(ScheduleUtils.getJobKey(job.getId(), job.getJobGroup()));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 暂停任务
     *
     * @param job
     */
    private void pauseSchedulerJob(SysJob job) {
        try {
            scheduler.pauseJob(ScheduleUtils.getJobKey(job.getId(), job.getJobGroup()));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除任务
     *
     * @param job
     */
    private void delSchedulerJob(SysJob job) {
        try {
            scheduler.deleteJob(ScheduleUtils.getJobKey(job.getId(), job.getJobGroup()));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 立即运行一次任务
     *
     * @param job
     */
    private void runSchedulerJob(SysJob job) {
        try {
            // 参数
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(ScheduleConstants.TASK_PROPERTIES, job);
            JobKey jobKey = ScheduleUtils.getJobKey(job.getId(), job.getJobGroup());
            if (scheduler.checkExists(jobKey)) {
                scheduler.triggerJob(jobKey, dataMap);
            }
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}
