package cn.daenx.myadmin.common.vo;

import lombok.Data;

/**
 * 通用修改状态
 */
@Data
public class SaveSendVo {
    /**
     * 现在是否可以发
     */
    private Boolean nowOk;

    /**
     * 下次发送需要等待的秒数
     */
    private long waitTime;
    /**
     * 状态
     */
    private String status;

}
