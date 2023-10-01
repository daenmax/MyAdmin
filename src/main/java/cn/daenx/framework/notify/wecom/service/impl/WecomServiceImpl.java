package cn.daenx.framework.notify.wecom.service.impl;

import cn.daenx.framework.common.vo.system.config.SysWecomConfigVo;
import cn.daenx.framework.notify.wecom.service.WecomService;
import cn.daenx.framework.notify.wecom.vo.WecomSendResult;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Service;


@Service("wecom")
public class WecomServiceImpl implements WecomService {
    /**
     * 发送企业微信群通知_实际算法
     *
     * @param configVo
     * @param content
     * @return
     */
    @Override
    public WecomSendResult sendMsg(SysWecomConfigVo configVo, String content) {
        if (ObjectUtil.isEmpty(configVo)) {
            return new WecomSendResult(false, 9999, "系统企业微信通知配置不可用", null);
        }
        String url = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=" + configVo.getKey();
        String body = HttpRequest.post(url).header("Content-Type", "application/json").body(content).execute().body();
        if (ObjectUtil.isEmpty(body)) {
            return new WecomSendResult(false, 9999, "请求接收为空", null);
        }
        JSONObject jsonObject = JSONObject.parseObject(body);
        Integer code = jsonObject.getInteger("errcode");
        if (code == 0) {
            return new WecomSendResult(true, code, jsonObject.getString("errmsg"), null);
        }
        return new WecomSendResult(false, code, jsonObject.getString("errmsg"), null);
    }
}
