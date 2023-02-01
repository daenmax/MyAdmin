package cn.daenx.myadmin.test.controller;

import cn.daenx.myadmin.test.po.TbUser;
import cn.daenx.myadmin.test.po.TestName;
import cn.daenx.myadmin.test.service.TbUserService;
import cn.daenx.myadmin.test.service.TestNameService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/name")
public class TestController {
    @Resource
    private TestNameService testNameService;
    @Resource
    private TbUserService tbUserService;

    @GetMapping("/list")
    public String list(){
        List<TestName> list = testNameService.list();
        System.out.println(list);
        return "aaa";
    }
    @GetMapping("/list2")
    public String list2(){
        List<TbUser> list = tbUserService.list();
        System.out.println(list);
        return "bbb";
    }
}
