package cn.daenx.framework.notify.feishu.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeishuSendResult {
    /**
     * 是否成功
     */
    private boolean isSuccess;
    /**
     * 飞书返回的
     */
    private Integer code;
    /**
     * 飞书返回的
     */
    private String msg;
    private String botName;
}
