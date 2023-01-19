package cn.daenx.myadmin.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/a")
    private String a() {
        log.info("你好");
        log.warn("你好");
        log.error("你好");
        return "ok";
    }
}
