package cn.daenx.framework.notify.sms.utils;

import cn.daenx.framework.common.constant.CommonConstant;
import cn.daenx.framework.common.constant.RedisConstant;
import cn.daenx.framework.common.utils.MyUtil;
import cn.daenx.framework.common.utils.RedisUtil;
import cn.daenx.framework.common.utils.ServletUtils;
import cn.daenx.framework.common.vo.CheckSendVo;
import cn.daenx.framework.notify.sms.vo.SmsSendResult;
import cn.daenx.framework.common.vo.system.config.SysConfigVo;
import cn.daenx.framework.common.vo.system.config.SysSendLimitConfigVo;
import cn.daenx.framework.common.vo.system.config.SysSmsConfigVo;
import cn.daenx.framework.notify.sms.service.SmsService;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Sms短信工具类
 *
 * @author DaenMax
 */
@Component
@Slf4j
public class SmsUtil {

    /**
     * 发送短信
     * <p>
     * <h4>关于phones参数</h4><br>
     * 腾讯云：格式为+[国家或地区码][手机号]，单次请求最多支持200个手机号且要求全为境内手机号或全为境外手机号，发送国内短信格式还支持0086、86或无任何国家或地区码的11位手机号码，必须加前缀，例如+86 <br><br>
     * 阿里云：国内短信：+/+86/0086/86或无任何前缀的11位手机号码，例如1390000****。国际/港澳台消息：国际区号+号码，例如852000012****。单次上限为1000个手机号码，前缀可不加，默认为+86
     * </p>
     * <p>
     * <h4>关于param参数</h4><br>
     * 腾讯云：例如模板为：例如模板为：验证码为：{1}，有效期为{2}分钟，如非本人操作，请忽略本短信。那么key=1，value=6666，key=2，value=5<br><br>
     * 阿里云：例如模板为：您的验证码为：${code}，请勿泄露于他人！那么key=code，value=1234
     * </p>
     *
     * @param phones     多个手机号用,隔开
     * @param templateId
     * @param param
     * @return
     */
    public static SmsSendResult sendSms(String phones, String templateId, Map<String, String> param) {
        log.info("发送短信ing，对象={}，templateId：{}，param：{}", phones, templateId, param);
        SysSmsConfigVo sysSmsConfigVo = getSysSmsConfigVo();
        if (ObjectUtil.isEmpty(sysSmsConfigVo)) {
            String msg = "系统短信配置不可用";
            log.info("发送短信{}，对象={}，templateId：{}，param：{}，原因：{}", false ? "成功" : "失败", phones, templateId, param, msg);
            return SmsSendResult.builder().isSuccess(false).msg(msg).build();
        }
        if (ObjectUtil.isEmpty(phones)) {
            String msg = "手机号不能为空";
            log.info("发送短信{}，平台={}，对象={}，templateId：{}，param：{}，原因：{}", false ? "成功" : "失败", sysSmsConfigVo.getConfig().getType(), phones, templateId, param, msg);
            return SmsSendResult.builder().isSuccess(false).msg(msg).build();
        }
        if (ObjectUtil.isEmpty(templateId)) {
            String msg = "模板ID不能为空";
            log.info("发送短信{}，平台={}，对象={}，templateId：{}，param：{}，原因：{}", false ? "成功" : "失败", sysSmsConfigVo.getConfig().getType(), phones, templateId, param, msg);
            return SmsSendResult.builder().isSuccess(false).msg(msg).build();
        }
        try {
            SmsService smsService = SpringUtil.getApplicationContext().getBean(sysSmsConfigVo.getConfig().getType(), SmsService.class);
            SmsSendResult smsSendResult = smsService.sendSms(sysSmsConfigVo.getPlatform().get(sysSmsConfigVo.getConfig().getType()), phones, templateId, param);
            log.info("发送短信{}，平台={}，对象={}，templateId：{}，param：{}，原因：{}", smsSendResult.isSuccess() ? "成功" : "失败", sysSmsConfigVo.getConfig().getType(), phones, templateId, param, smsSendResult.getMsg());
            return smsSendResult;
        } catch (NoSuchBeanDefinitionException e) {
            String msg = "未知的type";
            log.info("发送短信{}，平台={}，对象={}，templateId：{}，param：{}，原因：{}", false ? "成功" : "失败", sysSmsConfigVo.getConfig().getType(), phones, templateId, param, msg);
            return SmsSendResult.builder().isSuccess(false).msg(msg).build();
        }

    }

    /**
     * 发送短信
     * <p>
     * <h4>关于phones参数</h4><br>
     * 腾讯云：格式为+[国家或地区码][手机号]，单次请求最多支持200个手机号且要求全为境内手机号或全为境外手机号，发送国内短信格式还支持0086、86或无任何国家或地区码的11位手机号码，必须加前缀，例如+86 <br><br>
     * 阿里云：国内短信：+/+86/0086/86或无任何前缀的11位手机号码，例如1390000****。国际/港澳台消息：国际区号+号码，例如852000012****。单次上限为1000个手机号码，前缀可不加，默认为+86
     * </p>
     * <p>
     * <h4>关于param参数</h4><br>
     * 腾讯云：例如模板为：例如模板为：验证码为：{1}，有效期为{2}分钟，如非本人操作，请忽略本短信。那么key=1，value=6666，key=2，value=5<br><br>
     * 阿里云：例如模板为：您的验证码为：${code}，请勿泄露于他人！那么key=code，value=1234
     * </p>
     *
     * @param phones     多个手机号用,隔开
     * @param templateId
     * @param param
     * @param type       指定平台，aliyun=阿里云，tencent=腾讯云
     * @return
     */
    public static SmsSendResult sendSms(String phones, String templateId, Map<String, String> param, String type) {
        log.info("发送短信ing，平台={}，对象={}，templateId：{}，param：{}", type, phones, templateId, param);
        SysSmsConfigVo sysSmsConfigVo = getSysSmsConfigVo();
        if (ObjectUtil.isEmpty(sysSmsConfigVo)) {
            String msg = "系统短信配置不可用";
            log.info("发送短信{}，平台={}，对象={}，templateId：{}，param：{}，原因：{}", false ? "成功" : "失败", type, phones, templateId, param, msg);
            return SmsSendResult.builder().isSuccess(false).msg(msg).build();
        }
        if (ObjectUtil.isEmpty(phones)) {
            String msg = "手机号不能为空";
            log.info("发送短信{}，平台={}，对象={}，templateId：{}，param：{}，原因：{}", false ? "成功" : "失败", type, phones, templateId, param, msg);
            return SmsSendResult.builder().isSuccess(false).msg(msg).build();
        }
        if (ObjectUtil.isEmpty(templateId)) {
            String msg = "模板ID不能为空";
            log.info("发送短信{}，平台={}，对象={}，templateId：{}，param：{}，原因：{}", false ? "成功" : "失败", type, phones, templateId, param, msg);
            return SmsSendResult.builder().isSuccess(false).msg(msg).build();
        }
        if (ObjectUtil.isEmpty(type)) {
            String msg = "type不能为空";
            log.info("发送短信{}，平台={}，对象={}，templateId：{}，param：{}，原因：{}", false ? "成功" : "失败", type, phones, templateId, param, msg);
            return SmsSendResult.builder().isSuccess(false).msg(msg).build();
        }
        try {
            SmsService smsService = SpringUtil.getApplicationContext().getBean(type, SmsService.class);
            SmsSendResult smsSendResult = smsService.sendSms(sysSmsConfigVo.getPlatform().get(type), phones, templateId, param);
            log.info("发送短信{}，平台={}，对象={}，templateId：{}，param：{}，原因：{}", smsSendResult.isSuccess() ? "成功" : "失败", type, phones, templateId, param, smsSendResult.getMsg());
            return smsSendResult;
        } catch (NoSuchBeanDefinitionException e) {
            String msg = "未知的type";
            log.info("发送短信{}，平台={}，对象={}，templateId：{}，param：{}，原因：{}", false ? "成功" : "失败", type, phones, templateId, param, msg);
            return SmsSendResult.builder().isSuccess(false).msg(msg).build();
        }
    }

    /**
     * 从redis里获取系统邮箱配置信息
     * 不存在或者被禁用或者数量为0返回null
     *
     * @return
     */
    private static SysSmsConfigVo getSysSmsConfigVo() {
        Object object = RedisUtil.getValue(RedisConstant.CONFIG + "sys.sms.config");
        if (ObjectUtil.isEmpty(object)) {
            return null;
        }
        SysConfigVo sysConfig = JSON.parseObject(JSON.toJSONString(object), SysConfigVo.class);
        if (!sysConfig.getStatus().equals(CommonConstant.STATUS_NORMAL)) {
            return null;
        }
        SysSmsConfigVo sysSmsConfigVo = JSONObject.parseObject(sysConfig.getValue(), SysSmsConfigVo.class);
        return sysSmsConfigVo;
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
        if (sysSendLimitConfigVo.getSms().getDayMax() == -1) {
            return new CheckSendVo(true, 0, "可以进行发送");
        }
        if (sysSendLimitConfigVo.getSms().getNeedWait() == -1) {
            return new CheckSendVo(true, 0, "可以进行发送");
        }
        String key;
        if (sysSendLimitConfigVo.getSms().getLimitType() == 0) {
            key = userId;
        } else {
            key = ServletUtils.getClientIP();
        }
        Integer dayMax = sysSendLimitConfigVo.getSms().getDayMax();
        Integer needWait = sysSendLimitConfigVo.getSms().getNeedWait();
        //判断今天是否还可以发送
        Collection<String> yyyyMMdd = RedisUtil.getList(RedisConstant.SEND_PHONE + MyUtil.getDateStrByFormat("yyyyMMdd") + ":" + key + "*");
        if (yyyyMMdd.size() >= dayMax) {
            Integer remainSecondsOneDay = MyUtil.getRemainSecondsOneDay(LocalDateTime.now());
            String str = MyUtil.timeDistance(Long.valueOf(String.valueOf(remainSecondsOneDay * 1000)));
            return new CheckSendVo(false, remainSecondsOneDay, "今日请求过多，请于" + str + "后再试");
        }
        String lastSendTimeStr = (String) RedisUtil.getValue(RedisConstant.SEND_PHONE + key);
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
        if (sysSendLimitConfigVo.getSms().getDayMax() == -1) {
            return 0;
        }
        if (sysSendLimitConfigVo.getSms().getNeedWait() == -1) {
            return 0;
        }
        String key;
        if (sysSendLimitConfigVo.getSms().getLimitType() == 0) {
            key = userId;
        } else {
            key = ServletUtils.getClientIP();
        }
        Integer dayMax = sysSendLimitConfigVo.getSms().getDayMax();
        Integer needWait = sysSendLimitConfigVo.getSms().getNeedWait();
        String dateStrByFormat = MyUtil.getDateStrByFormat("yyyy-MM-dd HH:mm:ss");
        RedisUtil.setValue(RedisConstant.SEND_PHONE + MyUtil.getDateStrByFormat("yyyyMMdd") + ":" + key + ":" + IdUtil.fastSimpleUUID(), dateStrByFormat, 1L, TimeUnit.DAYS);
        RedisUtil.setValue(RedisConstant.SEND_PHONE + key, dateStrByFormat);
        //计算下次可以发的秒数
        Collection<String> yyyyMMdd = RedisUtil.getList(RedisConstant.SEND_PHONE + MyUtil.getDateStrByFormat("yyyyMMdd") + ":" + key + "*");
        if (yyyyMMdd.size() >= dayMax) {
            Integer remainSecondsOneDay = MyUtil.getRemainSecondsOneDay(LocalDateTime.now());
            return remainSecondsOneDay;
        }
        return needWait;
    }
}
