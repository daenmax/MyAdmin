package cn.daenx.server.api.admin.system;

import cn.daenx.framework.common.domain.vo.Result;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.excel.utils.ExcelUtil;
import cn.daenx.modules.system.domain.dto.sysConfig.SysConfigAddDto;
import cn.daenx.modules.system.domain.dto.sysConfig.SysConfigPageDto;
import cn.daenx.modules.system.domain.dto.sysConfig.SysConfigUpdDto;
import cn.daenx.modules.system.domain.po.SysConfig;
import cn.daenx.modules.system.service.SysConfigService;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/system/config")
public class SysConfigController {
    @Resource
    private SysConfigService sysConfigService;

    /**
     * 列表
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("system:config:page")
    @GetMapping(value = "/page")
    public Result<IPage<SysConfig>> page(SysConfigPageDto dto) {
        IPage<SysConfig> page = sysConfigService.getPage(dto);
        return Result.ok(page);
    }

    /**
     * 导出
     */
    @SaCheckPermission("system:config:export")
    @PostMapping("/exportData")
    public void exportData(SysConfigPageDto dto, HttpServletResponse response) {
        List<SysConfig> list = sysConfigService.getAll(dto);
        ExcelUtil.exportXlsx(response, "系统参数", "系统参数", list, SysConfig.class);
    }


    /**
     * 查询
     *
     * @param id
     * @return
     */
    @SaCheckPermission("system:config:query")
    @GetMapping(value = "/query")
    public Result<SysConfig> query(@RequestParam(name = "id", required = true) String id) {
        SysConfig sysConfig = sysConfigService.getInfo(id);
        return Result.ok(sysConfig);
    }

    /**
     * 修改
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("system:config:edit")
    @GetMapping(value = "/edit")
    public Result<Void> edit(@Validated @RequestBody SysConfigUpdDto dto) {
        sysConfigService.editInfo(dto);
        return Result.ok();
    }

    /**
     * 新增
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("system:config:add")
    @PostMapping("/add")
    public Result<Void> add(@Validated @RequestBody SysConfigAddDto dto) {
        sysConfigService.addInfo(dto);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission("system:config:del")
    @GetMapping(value = "/del")
    public Result<Void> del(@RequestBody List<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            throw new MyException("参数错误");
        }
        sysConfigService.deleteByIds(ids);
        return Result.ok();
    }

    /**
     * 刷新参数缓存
     *
     * @return
     */
    @SaCheckPermission("system:config:refreshCache")
    @PostMapping("/refreshCache")
    public Result<Void> refreshCache() {
        sysConfigService.refreshCache();
        return Result.ok();
    }

}
