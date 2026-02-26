package cn.daenx.server.lifecycle;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;


/**
 * 项目即将停止，执行
 */
@Component
@Slf4j
public class AppShutDown implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        //在这里写代码，停止你创建的一些线程之类的
        log.info("====项目准备停止...====");
    }


    @PreDestroy
    public void preDestroy() {
        log.info("====项目停止成功，再见====");
    }
}
