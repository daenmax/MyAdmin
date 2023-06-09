package cn.daenx.myadmin.system.domain.vo.system;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统登录错误次数限制信息
 */
@Data
@AllArgsConstructor
public class SysLoginFailInfoVo implements Serializable {

    /**
     * 错误次数将锁定，必填
     */
    private Integer failCount;

    /**
     * 锁定的秒数，必填
     */
    private Integer banSecond;



}
