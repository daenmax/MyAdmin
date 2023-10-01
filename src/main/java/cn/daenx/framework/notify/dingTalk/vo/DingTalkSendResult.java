package cn.daenx.framework.notify.dingTalk.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DingTalkSendResult {
    /**
     * 是否成功
     */
    private boolean isSuccess;
    /**
     * 钉钉返回的
     */
    private Integer code;
    /**
     * 钉钉返回的
     */
    private String msg;
    private String botName;
}
