package cn.daenx.myadmin.test.controller;

import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.test.po.TestData;
import cn.daenx.myadmin.test.service.TestDataService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/testData")
public class TestDataController {
    @Resource
    private TestDataService testDataService;


    @Parameter
    @GetMapping("/list")
    public Result list() {
        List<TestData> list = testDataService.list();
        System.out.println(list);
        return Result.ok(list);
    }

}
