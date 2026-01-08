package cn.daenx.modules.system.service.impl;

import cn.daenx.modules.system.domain.vo.sysJob.SysJobLogPageVo;
import cn.daenx.framework.common.constant.SystemConstant;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.domain.vo.system.other.SysJobLogVo;
import cn.daenx.modules.system.domain.po.SysJobLog;
import cn.daenx.modules.system.domain.dto.sysJob.SysJobLogPageDto;
import cn.daenx.modules.system.mapper.SysJobLogMapper;
import cn.daenx.modules.system.service.SysJobLogService;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysJobLogServiceImpl extends ServiceImpl<SysJobLogMapper, SysJobLog> implements SysJobLogService {
    @Resource
    private SysJobLogMapper sysJobLogMapper;

    @Async
    @EventListener
    public void saveLog(SysJobLogVo sysJobLogVo){
        SysJobLog sysJobLog = new SysJobLog();
        BeanUtils.copyProperties(sysJobLogVo, sysJobLog);
        sysJobLogMapper.insert(sysJobLog);
    }

    private QueryWrapper<SysJobLog> getWrapperQuery(SysJobLogPageDto vo) {
        QueryWrapper<SysJobLog> wrapper = new QueryWrapper<>();
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getId()), "sjl.id", vo.getId());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getJobName()), "sj.job_name", vo.getJobName());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getStatus()), "sjl.status", vo.getStatus());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getRemark()), "sjl.remark", vo.getRemark());
        String startTime = vo.getStartTime();
        String endTime = vo.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), "sjl.create_time", startTime, endTime);
        wrapper.eq("sjl.is_delete", SystemConstant.IS_DELETE_NO);
        return wrapper;
    }

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @Override
    public IPage<SysJobLogPageVo> getPage(SysJobLogPageDto vo) {
        QueryWrapper<SysJobLog> wrapperQuery = getWrapperQuery(vo);
        IPage<SysJobLogPageVo> iPage = sysJobLogMapper.getPageWrapper(vo.getPage(true), wrapperQuery);
        return iPage;
    }

    /**
     * 获取所有列表，用于导出
     *
     * @param vo
     * @return
     */
    @Override
    public List<SysJobLogPageVo> getAll(SysJobLogPageDto vo) {
        QueryWrapper<SysJobLog> wrapperQuery = getWrapperQuery(vo);
        List<SysJobLogPageVo> list = sysJobLogMapper.getAll(wrapperQuery);
        return list;
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @Override
    public SysJobLogPageVo getInfo(String id) {
        SysJobLogPageDto vo = new SysJobLogPageDto();
        vo.setId(id);
        QueryWrapper<SysJobLog> wrapperQuery = getWrapperQuery(vo);
        List<SysJobLogPageVo> list = sysJobLogMapper.getAll(wrapperQuery);
        if (list.size() == 0) {
            throw new MyException("数据不存在");
        }
        return list.get(0);
    }

    /**
     * 删除
     * 未加数据权限
     *
     * @param ids
     */

    @Override
    public void deleteByIds(List<String> ids) {
        int i = sysJobLogMapper.deleteByIds(ids);
        if (i < 1) {
            throw new MyException("删除失败");
        }
    }

    /**
     * 清空日志
     * 未加数据权限
     */
    @Override
    public void clean() {
        LambdaQueryWrapper<SysJobLog> wrapper = new LambdaQueryWrapper<>();
        sysJobLogMapper.delete(wrapper);
    }
}
