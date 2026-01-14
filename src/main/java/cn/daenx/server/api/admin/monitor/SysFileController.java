package cn.daenx.server.api.admin.monitor;

import cn.daenx.framework.common.constant.SystemConstant;
import cn.daenx.framework.common.constant.enums.LogOperType;
import cn.daenx.framework.common.domain.vo.Result;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.logSave.annotation.Log;
import cn.daenx.framework.oss.domain.UploadResult;
import cn.daenx.modules.system.domain.dto.sysFile.SysFilePageDto;
import cn.daenx.modules.system.domain.po.SysFile;
import cn.daenx.modules.system.domain.vo.sysFile.SysFilePageVo;
import cn.daenx.modules.system.service.SysFileService;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

/**
 * 文件管理
 */
@RestController
@RequestMapping("/monitor/file")
public class SysFileController {
    @Resource
    private SysFileService sysFileService;

    /**
     * 分页列表
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("monitor:file:page")
    @GetMapping(value = "/page")
    public Result<IPage<SysFilePageVo>> page(SysFilePageDto dto) {
        IPage<SysFilePageVo> page = sysFileService.getPage(dto);
        return Result.ok(page);
    }

    /**
     * 查询文件列表，根据文件ID数组
     *
     * @param fileIds 文件ID数组
     * @return
     */
    @SaCheckPermission("monitor:file:list")
    @GetMapping("/listByIds/{fileIds}")
    public Result<List<SysFile>> listByIds(@NotEmpty(message = "文件ID列表不能为空") @PathVariable String[] fileIds) {
        List<SysFile> list = sysFileService.getListByIds(Arrays.asList(fileIds));
        return Result.ok(list);
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission("monitor:file:del")
    @PostMapping("/del")
    public Result<Void> del(@RequestBody List<String> ids) {
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
    @Log(name = "上传文件", type = LogOperType.UPLOAD, recordParams = false, recordResult = true)
    @SaCheckPermission("monitor:file:upload")
    @PostMapping("/uploadFile")
    public Result<UploadResult> upload(@RequestPart("file") MultipartFile file) {
        UploadResult uploadResult = sysFileService.uploadFile(file, SystemConstant.FILE_FROM_USER);
        return Result.ok(uploadResult);
    }

    /**
     * 上传图片
     *
     * @return
     */
    @Log(name = "上传图片", type = LogOperType.UPLOAD, recordParams = false, recordResult = true)
    @SaCheckPermission("monitor:file:upload")
    @PostMapping("/uploadImage")
    public Result<UploadResult> avatar(@RequestPart("file") MultipartFile file) {
        UploadResult uploadResult = sysFileService.uploadImage(file, SystemConstant.FILE_FROM_USER);
        return Result.ok(uploadResult);
    }

    /**
     * 下载文件
     *
     * @param id 文件ID
     */
    @Log(name = "下载文件", type = LogOperType.DOWNLOAD, recordParams = true, recordResult = false)
    @SaCheckPermission("monitor:file:download")
    @GetMapping("/download/{id}")
    public void download(@PathVariable String id, HttpServletResponse response) {
        sysFileService.download(id, response);
    }
}
