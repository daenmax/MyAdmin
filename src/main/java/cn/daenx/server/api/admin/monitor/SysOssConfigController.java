package cn.daenx.server.api.admin.monitor;

import cn.daenx.framework.common.domain.dto.ComStatusUpdDto;
import cn.daenx.framework.common.domain.vo.Result;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.modules.system.domain.dto.sysOss.SysOssConfigAddDto;
import cn.daenx.modules.system.domain.dto.sysOss.SysOssConfigPageDto;
import cn.daenx.modules.system.domain.dto.sysOss.SysOssConfigUpdDto;
import cn.daenx.modules.system.domain.po.SysOssConfig;
import cn.daenx.modules.system.service.SysOssConfigService;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文件存储配置
 */
@RestController
@RequestMapping("/monitor/ossConfig")
public class SysOssConfigController {
    @Resource
    private SysOssConfigService sysOssConfigService;

    /**
     * 分页列表
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("monitor:ossConfig:page")
    @GetMapping(value = "/page")
    public Result<IPage<SysOssConfig>> page(SysOssConfigPageDto dto) {
        IPage<SysOssConfig> page = sysOssConfigService.getPage(dto);
        return Result.ok(page);
    }
    /**
     * 获取所有列表
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("monitor:ossConfig:list")
    @GetMapping(value = "/allList")
    public Result<List<SysOssConfig>> allList(SysOssConfigPageDto dto) {
        List<SysOssConfig> list = sysOssConfigService.getAll(dto);
        return Result.ok(list);
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @SaCheckPermission("monitor:ossConfig:query")
    @GetMapping(value = "/query")
    public Result<SysOssConfig> query(@RequestParam(name = "id", required = true) String id) {
        SysOssConfig sysOssConfig = sysOssConfigService.getInfo(id);
        return Result.ok(sysOssConfig);
    }

    /**
     * 修改
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("monitor:ossConfig:edit")
    @GetMapping(value = "/edit")
    public Result<Void> edit(@Validated @RequestBody SysOssConfigUpdDto dto) {
        sysOssConfigService.editInfo(dto);
        return Result.ok();
    }

    /**
     * 新增
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("monitor:ossConfig:add")
    @PostMapping("/add")
    public Result<Void> add(@Validated @RequestBody SysOssConfigAddDto dto) {
        sysOssConfigService.addInfo(dto);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission("monitor:ossConfig:del")
    @GetMapping(value = "/del")
    public Result<Void> del(@RequestBody List<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            throw new MyException("参数错误");
        }
        sysOssConfigService.deleteByIds(ids);
        return Result.ok();
    }

    /**
     * 修改配置状态
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("monitor:ossConfig:edit")
    @PostMapping("/changeStatus")
    public Result<Void> changeStatus(@Validated @RequestBody ComStatusUpdDto dto) {
        sysOssConfigService.changeStatus(dto);
        return Result.ok();
    }

    /**
     * 修改使用状态
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("monitor:ossConfig:edit")
    @PostMapping("/changeInUse")
    public Result<Void> changeInUse(@Validated @RequestBody ComStatusUpdDto dto) {
        sysOssConfigService.changeInUse(dto);
        return Result.ok();
    }

}
