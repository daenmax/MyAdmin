package cn.daenx.myadmin.test.controller;

import cn.daenx.myadmin.common.utils.RedisUtil;
import cn.daenx.myadmin.common.vo.Result;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.convert.Convert;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "功能测试", description = "对系统内的一些功能进行测试和展示使用方法")
@RestController
@SaIgnore
@RequestMapping("/test")
@Slf4j
public class TestController {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 测试redis
     *
     * @return
     */
    @GetMapping("/test1")
    public Result test1() {
        redisUtil.del("test");
        redisUtil.setValue("test", "你好啊", null, null);
        String test = (String) redisUtil.getValue("test");
        return Result.ok("查询成功", test);
    }
    /**
     * 测试redis
     *
     * @return
     */
    @GetMapping("/test2")
    public Result test2() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        redisUtil.setValue("abc",list,null,null);
        Object abc = redisUtil.getValue("abc");
        System.out.println(abc.getClass());
        List<String> convert = Convert.convert(ArrayList.class, abc);
        return Result.ok("查询成功");
    }
    /**
     * 测试redis
     *
     * @return
     */
    @GetMapping("/test3")
    public Result test3() {
        for (int i = 0; i < 4000; i++) {
            log.info("阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德" +
                    "阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德" +
                    "阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德" +
                    "");
        }
        return Result.ok("查询成功");
    }
}
