package cn.daenx.myadmin.common.liveCycle;

import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;


/**
 * 项目停止时，执行
 */
@Component
public class ApplicationShutDown {
    @PreDestroy
    public void preDestroy() {
        //在这里写代码，停止你创建的一些线程之类的
        System.out.println("====即将停止运行====");
    }
}
