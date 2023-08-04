package cn.daenx.common.utils;

import cn.daenx.common.constant.RedisConstant;
import cn.daenx.common.constant.SystemConstant;
import cn.daenx.common.vo.system.utils.DingTalkSendResult;
import cn.daenx.common.vo.system.config.SysConfigVo;
import cn.daenx.common.vo.system.config.SysDingTalkConfigVo;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 钉钉群通知工具类
 *
 * @author DaenMax
 */
@Component
@Slf4j
public class DingTalkUtil {

    /**
     * 发送钉钉群通知
     * text类型，如果需要其他消息类型，请自己组装报文，然后调用DingTalkSendResult方法
     *
     * @param botName 机器人名称，在系统参数里自己填的，多个用,隔开
     * @param msg     消息内容
     * @return
     */
    public static List<DingTalkSendResult> sendTalk(String botName, String msg) {
        log.info("发送钉钉头部开始ing，botName={}，msg：{}", botName, msg);
        List<SysDingTalkConfigVo> sysSmsConfigVoList = getSysSmsConfigVo(botName);
        if (CollUtil.isEmpty(sysSmsConfigVoList)) {
            log.info("发送钉钉{}，botName={}，msg：{}", false ? "成功" : "失败", botName, msg);
            return null;
        }
        List<DingTalkSendResult> list = new ArrayList<>();
        for (SysDingTalkConfigVo sysSmsConfigVo : sysSmsConfigVoList) {
            log.info("发送钉钉ing，botName={}，msg：{}", false ? "成功" : "失败", sysSmsConfigVo.getBotName(), msg);
            JSONObject req = new JSONObject();
            req.put("msgtype", "text");
            JSONObject text = new JSONObject();
            text.put("content", ObjectUtil.isEmpty(sysSmsConfigVo.getKeywords()) ? msg : sysSmsConfigVo.getKeywords() + msg);
            req.put("text", text);
            DingTalkSendResult dingTalkSendResult = sendMsg(sysSmsConfigVo, req.toJSONString());
            dingTalkSendResult.setBotName(botName);
            list.add(dingTalkSendResult);
            log.info("发送钉钉{}，botName={}，msg：{}，原因", dingTalkSendResult.isSuccess() ? "成功" : "失败", sysSmsConfigVo.getBotName(), msg, dingTalkSendResult.getMsg());
        }
        return list;
    }

    /**
     * 发送钉钉群通知
     * 自己组装报文，以便实现更多消息类型
     *
     * @param botName 机器人名称，在系统参数里自己填的，多个用,隔开
     * @param content JSON格式的数据
     * @return
     */
    public static List<DingTalkSendResult> sendTalkContent(String botName, String content) {
        log.info("发送钉钉头部开始ing，botName={}，content：{}", botName, content);
        List<SysDingTalkConfigVo> sysSmsConfigVoList = getSysSmsConfigVo(botName);
        if (CollUtil.isEmpty(sysSmsConfigVoList)) {
            log.info("发送钉钉{}，botName={}，content：{}", false ? "成功" : "失败", botName, content);
            return null;
        }
        List<DingTalkSendResult> list = new ArrayList<>();
        for (SysDingTalkConfigVo sysSmsConfigVo : sysSmsConfigVoList) {
            log.info("发送钉钉ing，botName={}，content：{}", false ? "成功" : "失败", sysSmsConfigVo.getBotName(), content);
            DingTalkSendResult dingTalkSendResult = sendMsg(sysSmsConfigVo, content);
            dingTalkSendResult.setBotName(botName);
            list.add(dingTalkSendResult);
            log.info("发送钉钉{}，botName={}，content：{}，原因", dingTalkSendResult.isSuccess() ? "成功" : "失败", sysSmsConfigVo.getBotName(), content, dingTalkSendResult.getMsg());
        }
        return list;
    }

    /**
     * 发送钉钉群通知_实际算法
     *
     * @param sysSmsConfigVo
     * @param content
     * @return
     */
    private static DingTalkSendResult sendMsg(SysDingTalkConfigVo sysSmsConfigVo, String content) {
        if (ObjectUtil.isEmpty(sysSmsConfigVo)) {
            return new DingTalkSendResult(false, 9999, "系统钉钉通知配置不可用", null);
        }
        String sign = "";
        if (ObjectUtil.isNotEmpty(sysSmsConfigVo.getSecret())) {
            try {
                sign = getSign(sysSmsConfigVo.getSecret());
            } catch (Exception e) {
                return new DingTalkSendResult(false, 9999, "计算签名失败", null);
            }
        }
        String url = "https://oapi.dingtalk.com/robot/send?access_token=" + sysSmsConfigVo.getAccessToken() + sign;
        String body = HttpRequest.post(url).header("Content-Type", "application/json").body(content).execute().body();
        if (ObjectUtil.isEmpty(body)) {
            return new DingTalkSendResult(false, 9999, "请求接收为空", null);
        }
        JSONObject jsonObject = JSONObject.parseObject(body);
        Integer errcode = jsonObject.getInteger("errcode");
        if (errcode == 0) {
            return new DingTalkSendResult(true, errcode, jsonObject.getString("errmsg"), null);
        }
        return new DingTalkSendResult(false, errcode, jsonObject.getString("errmsg"), null);
    }

    /**
     * 加签模式下，计算签名
     *
     * @param secret
     * @return
     * @throws Exception
     */
    private static String getSign(String secret) throws Exception {
        Long timestamp = System.currentTimeMillis();
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
        return "&sign=" + sign + "&timestamp=" + timestamp;
    }


    /**
     * 从redis里获取系统钉钉通知配置
     * 不存在或者被禁用或者数量为0返回null
     *
     * @param botName 机器人名称，在系统参数里自己填的，多个用,隔开
     * @return
     */
    private static List<SysDingTalkConfigVo> getSysSmsConfigVo(String botName) {
        Object object = RedisUtil.getValue(RedisConstant.CONFIG + "sys.dingTalk.config");
        if (ObjectUtil.isEmpty(object)) {
            return null;
        }
        SysConfigVo sysConfig = JSON.parseObject(JSON.toJSONString(object), SysConfigVo.class);
        if (!sysConfig.getStatus().equals(SystemConstant.STATUS_NORMAL)) {
            return null;
        }
        List<SysDingTalkConfigVo> list = new ArrayList<>();
        Set<String> set = Arrays.stream(botName.split(",")).collect(Collectors.toSet());
        String[] array = ArrayUtil.toArray(set, String.class);
        for (String name : array) {
            JSONObject jsonObject = JSONObject.parseObject(sysConfig.getValue());
            SysDingTalkConfigVo sysDingTalkConfigVo = jsonObject.getObject(name, SysDingTalkConfigVo.class);
            if (ObjectUtil.isNotEmpty(sysDingTalkConfigVo)) {
                sysDingTalkConfigVo.setBotName(name);
                list.add(sysDingTalkConfigVo);
            }
        }
        return list;
    }

}
