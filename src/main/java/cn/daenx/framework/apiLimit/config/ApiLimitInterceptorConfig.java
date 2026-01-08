package cn.daenx.framework.apiLimit.config;

import cn.daenx.framework.apiLimit.interceptor.ApiLimitInterceptor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Order(1)
@Configuration
@ConditionalOnProperty(prefix = "cache", value = "type", havingValue = "redis")
public class ApiLimitInterceptorConfig implements WebMvcConfigurer {
    @Resource
    private ApiLimitInterceptor apiLimitInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiLimitInterceptor).addPathPatterns("/**");
    }
}
