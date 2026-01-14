package cn.daenx.framework.notify.wecom.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WecomSendResult {
    /**
     * 是否成功
     */
    private boolean isSuccess;
    /**
     * 企业微信返回的
     */
    private Integer code;
    /**
     * 企业微信返回的
     */
    private String msg;
    private String botName;
}
