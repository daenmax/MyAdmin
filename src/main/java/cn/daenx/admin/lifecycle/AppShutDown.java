package cn.daenx.admin.lifecycle;

import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;


/**
 * 项目停止时，执行
 */
@Component
public class AppShutDown {
    @PreDestroy
    public void preDestroy() {
        //在这里写代码，停止你创建的一些线程之类的
        System.out.println("====即将停止运行====");
    }
}