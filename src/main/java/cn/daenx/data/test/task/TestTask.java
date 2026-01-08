package cn.daenx.data.test.task;

import cn.daenx.framework.common.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 测试定时任务
 */
@Component("TestTask")
@Slf4j
public class TestTask {
    public void multipleParams(String s, Boolean b, Long l, Double d, Integer i) {
        log.info("=============================执行多参方法");
    }

    public void oneParams(String params) {
        log.info("=============================执行有参方法：" + params);
    }

    public void noParams() throws InterruptedException {
        log.info("=============================执行无参方法-开始");
        Thread.sleep(1000);
        if (true) {
            throw new MyException("执行失败");
        }
        log.info("=============================执行无参方法-结束");
    }
}
