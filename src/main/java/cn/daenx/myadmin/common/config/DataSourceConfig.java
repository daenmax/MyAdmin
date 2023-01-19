package cn.daenx.myadmin.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean(name = "main")
    @ConfigurationProperties(prefix = "spring.datasource.druid.main")
    public DataSource dataSource1() {
        return new DruidDataSource();
    }

 
    @Bean(name = "slave")
    @ConfigurationProperties(prefix = "spring.datasource.druid.slave")
    public DataSource dataSource2() {
        return new DruidDataSource();
    }
}


