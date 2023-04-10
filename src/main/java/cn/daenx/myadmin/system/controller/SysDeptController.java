package cn.daenx.myadmin.system.controller;

import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.system.po.SysDept;
import cn.daenx.myadmin.system.po.SysMenu;
import cn.daenx.myadmin.system.service.LoginUtilService;
import cn.daenx.myadmin.system.service.SysDeptService;
import cn.daenx.myadmin.system.service.SysMenuService;
import cn.daenx.myadmin.system.vo.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.tree.Tree;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/system/dept")
public class SysDeptController {
    @Resource
    private SysDeptService sysDeptService;

    /**
     * 列表
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:dept:list")
    @GetMapping(value = "/list")
    public Result list(SysDeptPageVo vo) {
        List<SysDept> list = sysDeptService.getAll(vo);
        return Result.ok(list);
    }

    /**
     * 列表_排除自己
     *
     * @param id
     * @return
     */
    @SaCheckPermission("system:dept:list")
    @GetMapping(value = "/list/exclude/{id}")
    public Result excludeChild(@PathVariable(value = "id", required = false) String id) {
        List<SysDept> list = sysDeptService.getList(new SysDeptPageVo());
        List<SysDept> collect = list.stream().filter(item -> item.getId().equals(id)).collect(Collectors.toList());
        sysDeptService.removeList(list, collect);
        return Result.ok(list);
    }
    /**
     * 查询
     *
     * @param id
     * @return
     */
    @SaCheckPermission("system:dept:query")
    @GetMapping(value = "/{id}")
    public Result query(@PathVariable String id) {
        SysDept sysMenu = sysDeptService.getInfo(id);
        return Result.ok(sysMenu);
    }

    /**
     * 修改
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:dept:edit")
    @PutMapping
    public Result edit(@Validated @RequestBody SysDeptUpdVo vo) {
        sysDeptService.editInfo(vo);
        return Result.ok();
    }

    /**
     * 新增
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:dept:add")
    @PostMapping
    public Result add(@Validated @RequestBody SysDeptAddVo vo) {
        sysDeptService.addInfo(vo);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @SaCheckPermission("system:dept:remove")
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable("id") String id) {
        if (StringUtils.isBlank(id)) {
            throw new MyException("参数错误");
        }
        sysDeptService.deleteById(id);
        return Result.ok();
    }

}
