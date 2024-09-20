package cn.daenx.framework.notify.dingTalk.service.impl;

import cn.daenx.framework.common.vo.system.config.SysDingTalkConfigVo;
import cn.daenx.framework.notify.dingTalk.vo.DingTalkSendResult;
import cn.daenx.framework.notify.dingTalk.service.DingTalkService;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;

@Service("dingTalk")
public class DingTalkServiceImpl implements DingTalkService {
    /**
     * 发送钉钉群通知_实际算法
     *
     * @param configVo
     * @param content
     * @return
     */
    @Override
    public DingTalkSendResult sendMsg(SysDingTalkConfigVo configVo, String content) {
        if (ObjectUtil.isEmpty(configVo)) {
            return new DingTalkSendResult(false, 9999, "系统钉钉配置不可用", null);
        }
        String sign = "";
        if (ObjectUtil.isNotEmpty(configVo.getSecret())) {
            try {
                sign = getSign(configVo.getSecret());
            } catch (Exception e) {
                return new DingTalkSendResult(false, 9999, "计算签名失败", null);
            }
        }
        String url = "https://oapi.dingtalk.com/robot/send?access_token=" + configVo.getAccessToken() + sign;
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
}
