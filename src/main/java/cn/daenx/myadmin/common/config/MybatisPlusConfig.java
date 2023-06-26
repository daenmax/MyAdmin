package cn.daenx.myadmin.common.config;

import cn.daenx.myadmin.framework.dataScope.aspectj.DataScopeAspect;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {
    @Resource
    private DataScopeAspect dataScopeAspect;

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //分页插件
        interceptor.addInnerInterceptor(paginationInnerInterceptor());
        //乐观锁插件
        interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor());
        //数据权限插件
        interceptor.addInnerInterceptor(dataScopeInterceptor());
        return interceptor;
    }

    /**
     * 分页插件
     */
    private PaginationInnerInterceptor paginationInnerInterceptor() {
        PaginationInnerInterceptor interceptor = new PaginationInnerInterceptor();
        //分页的最大尺寸，-1为不限制
        interceptor.setMaxLimit(500L);
        //溢出总页数后是否进行处理(默认不处理
        interceptor.setOverflow(true);
        return interceptor;
    }

    /**
     * 乐观锁插件
     */
    public OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }


    /**
     * 数据权限插件
     */
    public DataPermissionInterceptor dataScopeInterceptor() {
        DataPermissionInterceptor dataPermissionInterceptor = new DataPermissionInterceptor();
        dataPermissionInterceptor.setDataPermissionHandler(dataScopeAspect);
        return dataPermissionInterceptor;
    }

}
