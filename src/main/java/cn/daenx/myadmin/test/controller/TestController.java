package cn.daenx.myadmin.test.controller;


import cn.daenx.myadmin.common.vo.Result;
import cn.dev33.satoken.annotation.SaIgnore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Tag(name = "功能测试", description = "对系统内的一些功能进行测试和展示使用方法")
@RestController
@SaIgnore
@RequestMapping("/test")
@Slf4j
public class TestController {

    /**
     * 测试接口
     *
     * @return
     */
    @Operation(method = "肯定是GET啦", summary = "测试接口", description = "我是description")
    @GetMapping("/test")
    public Result test() {
        return Result.ok("查询成功");
    }


}
