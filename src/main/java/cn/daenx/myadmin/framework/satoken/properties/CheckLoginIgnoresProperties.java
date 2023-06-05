package cn.daenx.myadmin.framework.satoken.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 设置忽略鉴权的地址
 */
@Data
@Component
@ConfigurationProperties(prefix = "check-login")
public class CheckLoginIgnoresProperties {

    /**
     * 忽略以下接口，不鉴权
     */
    private String[] ignores;
}
