package cn.daenx.server.api.admin.system;

import cn.daenx.framework.common.domain.vo.Result;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.excel.utils.ExcelUtil;
import cn.daenx.modules.system.domain.dto.sysDict.SysDictAddDto;
import cn.daenx.modules.system.domain.dto.sysDict.SysDictPageDto;
import cn.daenx.modules.system.domain.dto.sysDict.SysDictUpdDto;
import cn.daenx.modules.system.domain.po.SysDict;
import cn.daenx.modules.system.service.SysDictService;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典管理
 */
@RestController
@RequestMapping("/system/dict")
public class SysDictController {
    @Resource
    private SysDictService sysDictService;

    /**
     * 分页列表
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("system:dict:page")
    @GetMapping(value = "/page")
    public Result<IPage<SysDict>> page(SysDictPageDto dto) {
        IPage<SysDict> page = sysDictService.getPage(dto);
        return Result.ok(page);
    }

    /**
     * 导出
     */
    @SaCheckPermission("system:dict:export")
    @PostMapping("/exportData")
    public void exportData(SysDictPageDto dto, HttpServletResponse response) {
        List<SysDict> list = sysDictService.getAll(dto);
        ExcelUtil.exportXlsx(response, "字典", "字典编码", list, SysDict.class);
    }


    /**
     * 新增
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("system:dict:add")
    @PostMapping("/add")
    public Result<Void> add(@Validated @RequestBody SysDictAddDto dto) {
        sysDictService.addInfo(dto);
        return Result.ok();
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @SaCheckPermission("system:dict:query")
    @GetMapping(value = "/query")
    public Result<SysDict> query(@RequestParam(name = "id", required = true) String id) {
        SysDict sysDict = sysDictService.getInfo(id);
        return Result.ok(sysDict);
    }

    /**
     * 修改
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("system:dict:edit")
    @GetMapping(value = "/edit")
    public Result<Void> edit(@Validated @RequestBody SysDictUpdDto dto) {
        sysDictService.editInfo(dto);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission("system:dict:del")
    @GetMapping(value = "/del")
    public Result<Void> del(@RequestBody List<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            throw new MyException("参数错误");
        }
        sysDictService.deleteByIds(ids);
        return Result.ok();
    }


    /**
     * 刷新字典缓存
     *
     * @return
     */
    @SaCheckPermission("system:dict:refreshCache")
    @PostMapping("/refreshCache")
    public Result<Void> refreshCache() {
        sysDictService.refreshCache();
        return Result.ok();
    }


    /**
     * 获取字典选择框列表
     *
     * @return
     */
    @GetMapping(value = "/optionSelect")
    public Result<List<SysDict>> optionSelect() {
        List<SysDict> list = sysDictService.getAll(new SysDictPageDto());
        return Result.ok(list);
    }
}
