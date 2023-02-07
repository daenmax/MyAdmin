package cn.daenx.myadmin.test.controller;

import cn.daenx.myadmin.test.po.TbUser;
import cn.daenx.myadmin.test.po.TestName;
import cn.daenx.myadmin.test.service.TbUserService;
import cn.daenx.myadmin.test.service.TestNameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Tag(name = "姓名管理",description = "对系统内的姓名进行增删改查")
@RestController
@RequestMapping("/name")
public class TestController {
    @Resource
    private TestNameService testNameService;
    @Resource
    private TbUserService tbUserService;

    @Operation(method = "肯定是GET啦",summary = "获取数据源一的数据",description = "我是description")
    @Parameter
    @GetMapping("/list1")
    public String list1(){
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
