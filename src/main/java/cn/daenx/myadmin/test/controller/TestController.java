package cn.daenx.myadmin.test.controller;

import cn.daenx.myadmin.common.oss.core.OssClient;
import cn.daenx.myadmin.common.oss.utils.OssUtil;
import cn.daenx.myadmin.common.oss.vo.UploadResult;
import cn.daenx.myadmin.common.utils.RedisUtil;
import cn.daenx.myadmin.common.vo.Result;
import cn.dev33.satoken.annotation.SaIgnore;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Tag(name = "功能测试", description = "对系统内的一些功能进行测试和展示使用方法")
@RestController
@SaIgnore
@RequestMapping("/test")
@Slf4j
public class TestController {

    /**
     * 测试
     *
     * @return
     */
    @GetMapping("/test")
    public Result test() {
        return Result.ok("查询成功");
    }


}
