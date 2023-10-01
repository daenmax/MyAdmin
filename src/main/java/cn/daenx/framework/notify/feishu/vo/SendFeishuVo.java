package cn.daenx.framework.notify.feishu.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 飞书发送消息
 */
@Data
public class SendFeishuVo {
    /**
     * 机器人名称，在系统参数里自己填的
     */
    @NotBlank(message = "机器人名称不能为空")
    private String botName;

    /**
     * 消息内容
     */
    @NotBlank(message = "消息内容不能为空")
    private String msg;

}
