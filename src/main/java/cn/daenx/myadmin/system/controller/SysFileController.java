package cn.daenx.myadmin.system.controller;

import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.oss.vo.UploadResult;
import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.daenx.myadmin.system.dto.SysFilePageDto;
import cn.daenx.myadmin.system.po.SysFile;
import cn.daenx.myadmin.system.service.SysFileService;
import cn.daenx.myadmin.system.vo.SysFilePageVo;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/system/file")
public class SysFileController {
    @Resource
    private SysFileService sysFileService;

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:file:list")
    @GetMapping(value = "/list")
    public Result list(SysFilePageVo vo) {
        IPage<SysFilePageDto> page = sysFileService.getPage(vo);
        return Result.ok(page);
    }

    /**
     * 查询文件列表，根据文件ID数组
     *
     * @param fileIds 文件ID数组
     * @return
     */
    @SaCheckPermission("system:file:list")
    @GetMapping("/listByIds/{fileIds}")
    public Result listByIds(@NotEmpty(message = "文件ID列表不能为空") @PathVariable String[] fileIds) {
        List<SysFile> list = sysFileService.getListByIds(Arrays.asList(fileIds));
        return Result.ok(list);
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission("system:file:remove")
    @DeleteMapping()
    public Result remove(@RequestBody List<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            throw new MyException("参数错误");
        }
        sysFileService.deleteByIds(ids);
        return Result.ok();
    }

    /**
     * 上传文件
     *
     * @return
     */
    @SaCheckPermission("system:file:upload")
    @PostMapping("/uploadFile")
    public Result upload(@RequestPart("file") MultipartFile file) {
        UploadResult uploadResult = sysFileService.uploadFile(file, SystemConstant.FILE_FROM_USER);
        return Result.ok(uploadResult);
    }

    /**
     * 上传图片
     *
     * @return
     */
    @SaCheckPermission("system:file:upload")
    @PostMapping("/uploadImage")
    public Result avatar(@RequestPart("file") MultipartFile file) {
        UploadResult uploadResult = sysFileService.uploadImage(file, SystemConstant.FILE_FROM_USER);
        return Result.ok(uploadResult);
    }

    /**
     * 下载文件
     *
     * @param id 文件ID
     */
    @SaCheckPermission("system:file:download")
    @GetMapping("/download/{id}")
    public void download(@PathVariable String id, HttpServletResponse response) throws IOException {
        sysFileService.download(id, response);
    }
}
