package cn.daenx.myadmin.test.controller;

import cn.daenx.myadmin.common.utils.RedisUtil;
import cn.daenx.myadmin.common.vo.Result;
import cn.dev33.satoken.annotation.SaIgnore;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "功能测试", description = "对系统内的一些功能进行测试和展示使用方法")
@RestController
@SaIgnore
@RequestMapping("/test")
@Slf4j
public class TestController {


    /**
     * 测试redis
     *
     * @return
     */
    @GetMapping("/test4")
    public Result test4() {
        RedisUtil.del("test");
        RedisUtil.setValue("test", "你好啊", null, null);
        String test = (String) RedisUtil.getValue("test");
        return Result.ok("查询成功", test);
    }


}
