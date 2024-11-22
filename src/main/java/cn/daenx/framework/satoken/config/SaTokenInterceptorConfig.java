package cn.daenx.framework.satoken.config;

import cn.daenx.framework.satoken.properties.CheckLoginIgnoresProperties;
import cn.dev33.satoken.fun.SaParamFunction;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.router.SaRouterStaff;
import cn.dev33.satoken.stp.StpUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

@Configuration
@Slf4j
@Order(2)
public class SaTokenInterceptorConfig implements WebMvcConfigurer {
    @Resource
    private CheckLoginIgnoresProperties checkLoginIgnoresProperties;

    /**
     * 注册路由拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //new一个拦截器
        SaInterceptor saInterceptor = new SaInterceptor(new SaParamFunction<Object>() {
            @Override
            public void run(Object o) {
                //放行前端静态资源目录
                if (o instanceof ParameterizableViewController) {
                    ParameterizableViewController controller = (ParameterizableViewController) o;
                    String viewName = controller.getViewName();
                    if ("forward:index.html".equals(viewName)) {
                        return;
                    }
                }
                //检查所有的路径
                SaRouter.match("/**").check(new SaParamFunction<SaRouterStaff>() {
                    //以下是检查代码
                    @Override
                    public void run(SaRouterStaff saRouterStaff) {
                        // 检查是否登录
                        StpUtil.checkLogin();
                    }
                });
            }
        });

        // 将上面new的拦截器注册进去
        registry.addInterceptor(saInterceptor)
                // 拦截所有路径
                .addPathPatterns("/**")
                // 设置不拦截的路径
                .excludePathPatterns(checkLoginIgnoresProperties.getIgnores());
    }
}
