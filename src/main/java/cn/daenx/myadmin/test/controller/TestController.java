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
     * 测试OSS
     *
     * @return
     */
    @PostMapping("/upload")
    public Result upload(@RequestPart("file") MultipartFile file) throws IOException {
        //文件名，例如：大恩的头像.jpg
        String originalName = file.getOriginalFilename();
        //后缀，例如：.jpg
        String suffix = StringUtils.substring(originalName, originalName.lastIndexOf("."), originalName.length());
        //文件类型，例如：image/jpeg
        String contentType = file.getContentType();
        OssClient ossClient = OssUtil.getOssClient();
        UploadResult upload = ossClient.uploadSuffix(file.getBytes(), suffix, contentType);
        return Result.ok("上传成功", upload);
    }


    /**
     * 测试OSS获取文件列表
     *
     * @return
     */
    @GetMapping("/fileList")
    public Result fileList() {
        OssClient ossClient = OssUtil.getOssClient();
        List<S3ObjectSummary> objectList = ossClient.getObjectList();
        return Result.ok("查询成功", objectList);
    }

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
