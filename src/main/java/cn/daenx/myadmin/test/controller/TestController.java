package cn.daenx.myadmin.test.controller;

import cn.daenx.myadmin.common.utils.RedisUtil;
import cn.daenx.myadmin.common.vo.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "功能测试", description = "对系统内的一些功能进行测试和展示使用方法")
@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    private RedisUtil redisUtil;


    /**
     * 测试redis
     *
     * @return
     */
    @GetMapping("/test1")
    public Result test1() {
        redisUtil.del("test");
        redisUtil.set("test", "你好啊");
        String test = (String) redisUtil.get("test");
        return Result.ok("查询成功",test);
    }
}
