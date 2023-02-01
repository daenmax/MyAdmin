package cn.daenx.myadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableAspectJAutoProxy
@SpringBootApplication
public class MyAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyAdminApplication.class, args);

    }

}
