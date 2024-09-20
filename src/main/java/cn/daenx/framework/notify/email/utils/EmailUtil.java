package cn.daenx.framework.notify.email.utils;

import cn.daenx.framework.common.constant.CommonConstant;
import cn.daenx.framework.common.constant.RedisConstant;
import cn.daenx.framework.common.constant.SystemConstant;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.utils.MyUtil;
import cn.daenx.framework.common.utils.RedisUtil;
import cn.daenx.framework.common.utils.ServletUtils;
import cn.daenx.framework.common.vo.CheckSendVo;
import cn.daenx.framework.common.vo.system.config.SysConfigVo;
import cn.daenx.framework.common.vo.system.config.SysEmailConfigVo;
import cn.daenx.framework.common.vo.system.config.SysSendLimitConfigVo;
import cn.daenx.framework.notify.email.service.EmailService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.WeightRandom;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Email工具类
 *
 * @author DaenMax
 */
@Component
@Slf4j
public class EmailUtil {

    /**
     * 邮箱轮询队列脚本
     */
    private static RedisScript<String> nextEmailScript;

    @Resource
    public void setNextEmailScript(RedisScript<String> nextEmailScript) {
        EmailUtil.nextEmailScript = nextEmailScript;
    }


    /**
     * 发送邮件
     * 按照系统邮箱配置的使用模式进行选择邮箱号
     *
     * @param toEmail  多个用,隔开
     * @param subject
     * @param content
     * @param isHtml   是否是HTML
     * @param fileList 附件内容，留空则无
     * @return
     */
    public static Boolean sendEmail(String toEmail, String subject, String content, Boolean isHtml, List<File> fileList) {
        log.info("发送邮件ing，对象={}，标题：{}，内容：{}", toEmail, subject, content);
        SysEmailConfigVo.Email email = getOneEmailConfig();
        if (ObjectUtil.isEmpty(email)) {
            log.info("发送邮件{}，对象={}，标题：{}，内容：{}", false ? "成功" : "失败", toEmail, subject, content);
            throw new MyException("未找到对应配置");
        }
        try {
            EmailService emailService = SpringUtil.getApplicationContext().getBean("email", EmailService.class);
            Boolean aBoolean = emailService.sendMail(email, toEmail, subject, content, isHtml, fileList);
            log.info("发送邮件{}，发信={}，对象={}，标题：{}，内容：{}", aBoolean ? "成功" : "失败", email.getEmail(), toEmail, subject, content);
            return aBoolean;
        } catch (NoSuchBeanDefinitionException e) {
            log.info("发送邮件{}，对象={}，标题：{}，内容：{}，接口实现类未找到", false ? "成功" : "失败", toEmail, subject, content);
            throw new MyException("接口实现类未找到");
        }
    }

    /**
     * 发送邮件
     * 指定邮箱号
     *
     * @param toEmail   多个用,隔开
     * @param subject
     * @param content
     * @param isHtml    是否是HTML
     * @param fileList  附件内容，留空则无
     * @param fromEmail 指定要使用的在系统配置里的发信邮箱
     * @return
     */
    public static Boolean sendEmail(String toEmail, String subject, String content, Boolean isHtml, List<File> fileList, String fromEmail) {
        log.info("发送邮件ing，对象={}，标题：{}，内容：{}", toEmail, subject, content);
        SysEmailConfigVo.Email email = getOneEmailConfig(fromEmail);
        if (ObjectUtil.isEmpty(email)) {
            log.info("发送邮件{}，对象={}，标题：{}，内容：{}", false ? "成功" : "失败", toEmail, subject, content);
            throw new MyException("未找到对应配置");
        }
        try {
            EmailService emailService = SpringUtil.getApplicationContext().getBean("email", EmailService.class);
            Boolean aBoolean = emailService.sendMail(email, toEmail, subject, content, isHtml, fileList);
            log.info("发送邮件{}，发信={}，对象={}，标题：{}，内容：{}", aBoolean ? "成功" : "失败", email.getEmail(), toEmail, subject, content);
            return aBoolean;
        } catch (NoSuchBeanDefinitionException e) {
            log.info("发送邮件{}，对象={}，标题：{}，内容：{}，接口实现类未找到", false ? "成功" : "失败", toEmail, subject, content);
            throw new MyException("接口实现类未找到");
        }

    }


    /**
     * 从redis里获取系统邮箱配置信息
     * 不存在或者被禁用或者数量为0返回null
     *
     * @return
     */
    private static SysEmailConfigVo getSysEmailConfigVo() {
        Object object = RedisUtil.getValue(RedisConstant.CONFIG + "sys.email.config");
        if (ObjectUtil.isEmpty(object)) {
            return null;
        }
        SysConfigVo sysConfig = JSON.parseObject(JSON.toJSONString(object), SysConfigVo.class);
        if (!sysConfig.getStatus().equals(CommonConstant.STATUS_NORMAL)) {
            return null;
        }
        SysEmailConfigVo sysEmailConfigVo = JSONObject.parseObject(sysConfig.getValue(), SysEmailConfigVo.class);
        if (sysEmailConfigVo.getEmails().size() == 0) {
            return null;
        }
        return sysEmailConfigVo;
    }

    /**
     * 获得一个邮箱对象
     * 不存在或者被禁用或者数量为0返回null
     *
     * @return
     */
    private static SysEmailConfigVo.Email getOneEmailConfig() {
        SysEmailConfigVo sysEmailConfigVo = getSysEmailConfigVo();
        if (ObjectUtil.isEmpty(sysEmailConfigVo)) {
            return null;
        }
        List<SysEmailConfigVo.Email> list = sysEmailConfigVo.getEmails().stream().filter(item -> "true".equals(item.getEnable())).collect(Collectors.toList());
        if (list.size() == 1) {
            return sysEmailConfigVo.getEmails().get(0);
        }
        SysEmailConfigVo.Email oneEmail = getOneEmailByMode(sysEmailConfigVo.getConfig(), list);
        return oneEmail;
    }

    /**
     * 获得一个邮箱对象
     * 不存在或者被禁用或者数量为0返回null
     *
     * @param email 指定邮箱
     * @return
     */
    private static SysEmailConfigVo.Email getOneEmailConfig(String email) {
        SysEmailConfigVo sysEmailConfigVo = getSysEmailConfigVo();
        if (ObjectUtil.isEmpty(sysEmailConfigVo)) {
            return null;
        }
        List<SysEmailConfigVo.Email> list = sysEmailConfigVo.getEmails().stream().filter(item -> "true".equals(item.getEnable()) && email.equals(item.getEmail())).collect(Collectors.toList());
        if (list.size() > 0) {
            return sysEmailConfigVo.getEmails().get(0);
        }
        return null;
    }

    /**
     * 邮箱使用模式_轮询时，弹出一个邮箱并重新放进去
     *
     * @return
     */
    private static String rightPopAndLeftPushEmail() {
        //这里两种方式，自己决定用哪一种哈~
        //使用redis lua方式
        String email = RedisUtil.getRedisTemplate().execute(nextEmailScript, CollUtil.newArrayList(SystemConstant.EMAIL_POLL_KEY), "");
        //使用 rightPopAndLeftPush方式
//        String email = (String) RedisUtil.rightPopAndLeftPush(SystemConstant.EMAIL_POLL_KEY);
        return email;
    }


    /**
     * 通过邮箱使用模式获得一个邮箱对象
     * 内部调用
     *
     * @param config
     * @param list
     * @return
     */
    private static SysEmailConfigVo.Email getOneEmailByMode(SysEmailConfigVo.Config config, List<SysEmailConfigVo.Email> list) {
        String email;
        if (SystemConstant.EMAIL_MODE_POLL.equals(config.getMode())) {
            //邮箱使用模式_轮询
            email = rightPopAndLeftPushEmail();
        } else if (SystemConstant.EMAIL_MODE_RANDOM.equals(config.getMode())) {
            //邮箱使用模式_完全随机
            List<String> emails = MyUtil.joinToList(list, SysEmailConfigVo.Email::getEmail);
            email = RandomUtil.randomEle(emails);
        } else if (SystemConstant.EMAIL_MODE_RANDOM_WEIGHT.equals(config.getMode())) {
            //邮箱使用模式_权重随机
            List<WeightRandom.WeightObj<String>> weightObjs = new ArrayList<>();
            for (SysEmailConfigVo.Email email1 : list) {
                weightObjs.add(new WeightRandom.WeightObj<>(email1.getEmail(), Double.valueOf(email1.getWeight())));
            }
            email = RandomUtil.weightRandom(weightObjs).next();
        } else {
            email = null;
        }
        if (email == null) {
            return null;
        }
        List<SysEmailConfigVo.Email> emailList = list.stream().filter(item -> email.equals(item.getEmail())).collect(Collectors.toList());
        if (emailList.size() > 0) {
            return emailList.get(0);
        }
        return null;
    }

    /**
     * 根据用户ID判断是否可以发送
     * 返回所需要等待的秒数，0=马上可以发
     *
     * @param userId
     * @return
     */
    public static CheckSendVo checkSendByUserId(String userId, SysSendLimitConfigVo sysSendLimitConfigVo) {
        if (sysSendLimitConfigVo == null) {
            return new CheckSendVo(true, 0, "可以进行发送");
        }
        if (sysSendLimitConfigVo.getEmail().getDayMax() == -1) {
            return new CheckSendVo(true, 0, "可以进行发送");
        }
        if (sysSendLimitConfigVo.getEmail().getNeedWait() == -1) {
            return new CheckSendVo(true, 0, "可以进行发送");
        }
        String key;
        if (sysSendLimitConfigVo.getEmail().getLimitType() == 0) {
            key = userId;
        } else {
            key = ServletUtils.getClientIP();
        }
        Integer dayMax = sysSendLimitConfigVo.getEmail().getDayMax();
        Integer needWait = sysSendLimitConfigVo.getEmail().getNeedWait();

        //判断今天是否还可以发送
        Collection<String> yyyyMMdd = RedisUtil.getList(RedisConstant.SEND_EMAIL + MyUtil.getDateStrByFormat("yyyyMMdd") + ":" + key + "*");
        if (yyyyMMdd.size() >= dayMax) {
            Integer remainSecondsOneDay = MyUtil.getRemainSecondsOneDay(LocalDateTime.now());
            String str = MyUtil.timeDistance(Long.valueOf(String.valueOf(remainSecondsOneDay * 1000)));
            return new CheckSendVo(false, remainSecondsOneDay, "今日请求过多，请于" + str + "后再试");
        }
        String lastSendTimeStr = (String) RedisUtil.getValue(RedisConstant.SEND_EMAIL + key);
        if (ObjectUtil.isEmpty(lastSendTimeStr)) {
            return new CheckSendVo(true, 0, "可以进行发送");
        }
        LocalDateTime lastSendTime = MyUtil.strToLocalDateTime(lastSendTimeStr, "yyyy-MM-dd HH:mm:ss");
        Integer diffSec = MyUtil.getDiffSec(LocalDateTime.now(), lastSendTime);
        if (diffSec < needWait) {
            int sec = needWait - diffSec;
            String str = MyUtil.timeDistance(Long.valueOf(String.valueOf(sec * 1000)));
            return new CheckSendVo(false, sec, "请求过于频繁，请于" + str + "后再试");
        }
        return new CheckSendVo(true, 0, "可以进行发送");
    }

    /**
     * 记录
     *
     * @param userId
     * @return
     */
    public static Integer saveSendByUserId(String userId, SysSendLimitConfigVo sysSendLimitConfigVo) {
        if (sysSendLimitConfigVo == null) {
            return 0;
        }
        if (sysSendLimitConfigVo.getEmail().getDayMax() == -1) {
            return 0;
        }
        if (sysSendLimitConfigVo.getEmail().getNeedWait() == -1) {
            return 0;
        }
        String key;
        if (sysSendLimitConfigVo.getEmail().getLimitType() == 0) {
            key = userId;
        } else {
            key = ServletUtils.getClientIP();
        }
        Integer dayMax = sysSendLimitConfigVo.getEmail().getDayMax();
        Integer needWait = sysSendLimitConfigVo.getEmail().getNeedWait();
        String dateStrByFormat = MyUtil.getDateStrByFormat("yyyy-MM-dd HH:mm:ss");
        RedisUtil.setValue(RedisConstant.SEND_EMAIL + MyUtil.getDateStrByFormat("yyyyMMdd") + ":" + key + ":" + IdUtil.fastSimpleUUID(), dateStrByFormat, 1L, TimeUnit.DAYS);
        RedisUtil.setValue(RedisConstant.SEND_EMAIL + key, dateStrByFormat);
        //计算下次可以发的秒数
        Collection<String> yyyyMMdd = RedisUtil.getList(RedisConstant.SEND_EMAIL + MyUtil.getDateStrByFormat("yyyyMMdd") + ":" + key + "*");
        if (yyyyMMdd.size() >= dayMax) {
            Integer remainSecondsOneDay = MyUtil.getRemainSecondsOneDay(LocalDateTime.now());
            return remainSecondsOneDay;
        }
        return needWait;
    }


}
