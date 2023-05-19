package cn.daenx.myadmin.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 检查是否可以发送邮件、短信
 */
@Data
@AllArgsConstructor
public class CheckSendVo {
    /**
     * 现在是否可以发
     */
    private Boolean nowOk;

    /**
     * 还需要等待的秒数
     */
    private long waitTime;

    /**
     * 描述文本
     */
    private String msg;

}
