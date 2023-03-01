package cn.daenx.myadmin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableAspectJAutoProxy
@SpringBootApplication
@Slf4j
public class MyAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyAdminApplication.class, args);
        log.info("我是info");
        log.warn("我是warn");
        log.error("我是error");
    }

}
