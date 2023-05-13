package cn.daenx.myadmin.common.utils;

import cn.daenx.myadmin.common.constant.RedisConstant;
import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.daenx.myadmin.system.po.SysConfig;
import cn.daenx.myadmin.system.vo.system.DingTalkSendResult;
import cn.daenx.myadmin.system.vo.system.SysDingTalkConfigVo;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;

/**
 * 钉钉群通知工具类
 *
 * @author DaenMax
 */
public class DingTalkUtil {

    /**
     * 发送钉钉群通知
     * text类型，如果需要其他消息类型，请自己组装报文，然后调用DingTalkSendResult方法
     *
     * @param msg 消息内容
     * @return
     */
    public static DingTalkSendResult sendTalk(String msg) {
        SysDingTalkConfigVo sysSmsConfigVo = getSysSmsConfigVo();
        JSONObject req = new JSONObject();
        req.put("msgtype", "text");
        JSONObject text = new JSONObject();
        text.put("content", ObjectUtil.isEmpty(sysSmsConfigVo.getKeywords()) ? msg : sysSmsConfigVo.getKeywords() + msg);
        req.put("text", text);
        return sendMsg(sysSmsConfigVo, req.toJSONString());
    }

    /**
     * 发送钉钉群通知
     * 自己组装报文，以便实现更多消息类型
     *
     * @param content JSON格式的数据
     * @return
     */
    public static DingTalkSendResult sendTalkContent(String content) {
        SysDingTalkConfigVo sysSmsConfigVo = getSysSmsConfigVo();
        return sendMsg(sysSmsConfigVo, content);
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
            return new DingTalkSendResult(false, 9999, "系统钉钉通知配置不可用");
        }
        String sign = "";
        if (ObjectUtil.isNotEmpty(sysSmsConfigVo.getSecret())) {
            try {
                sign = getSign(sysSmsConfigVo.getSecret());
            } catch (Exception e) {
                return new DingTalkSendResult(false, 9999, "计算签名失败");
            }
        }
        String url = "https://oapi.dingtalk.com/robot/send?access_token=" + sysSmsConfigVo.getAccessToken() + sign;
        String body = HttpRequest.post(url).header("Content-Type", "application/json").body(content).execute().body();
        if (ObjectUtil.isEmpty(body)) {
            return new DingTalkSendResult(false, 9999, "请求接收为空");
        }
        JSONObject jsonObject = JSONObject.parseObject(body);
        Integer errcode = jsonObject.getInteger("errcode");
        if (errcode == 0) {
            return new DingTalkSendResult(true, errcode, jsonObject.getString("errmsg"));
        }
        return new DingTalkSendResult(false, errcode, jsonObject.getString("errmsg"));
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
     * @return
     */
    private static SysDingTalkConfigVo getSysSmsConfigVo() {
        Object object = RedisUtil.getValue(RedisConstant.CONFIG + "sys.dingTalk.config");
        if (ObjectUtil.isEmpty(object)) {
            return null;
        }
        SysConfig sysConfig = JSON.parseObject(JSON.toJSONString(object), SysConfig.class);
        if (!sysConfig.getStatus().equals(SystemConstant.STATUS_NORMAL)) {
            return null;
        }
        SysDingTalkConfigVo sysDingTalkConfigVo = JSONObject.parseObject(sysConfig.getValue(), SysDingTalkConfigVo.class);
        return sysDingTalkConfigVo;
    }

}
