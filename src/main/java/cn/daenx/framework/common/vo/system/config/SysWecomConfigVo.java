package cn.daenx.framework.common.vo.system.config;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统企业微信通知配置
 */
@Data
@AllArgsConstructor
public class SysWecomConfigVo implements Serializable {



    /**
     * WEBHOOK连接里key=后面的参数，必填
     */
    private String key;

    /**
     * 仅仅是备注，无其他作用
     */
    private String remark;

    /**
     * 此参数不是真的存在
     */
    private String botName;
}
