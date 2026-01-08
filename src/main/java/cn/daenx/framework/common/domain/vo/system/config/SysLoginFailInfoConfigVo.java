package cn.daenx.framework.common.domain.vo.system.config;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统登录错误次数限制信息
 */
@Data
@AllArgsConstructor
public class SysLoginFailInfoConfigVo implements Serializable {

    /**
     * 错误次数将锁定，必填
     */
    private Integer failCount;

    /**
     * 锁定的秒数，必填
     */
    private Integer banSecond;



}
