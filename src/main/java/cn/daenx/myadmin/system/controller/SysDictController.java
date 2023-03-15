package cn.daenx.myadmin.system.controller;

import cn.daenx.myadmin.common.utils.ExcelUtil;
import cn.daenx.myadmin.common.utils.MyUtil;
import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.system.po.SysDict;
import cn.daenx.myadmin.system.po.SysDictDetail;
import cn.daenx.myadmin.system.service.SysDictDetailService;
import cn.daenx.myadmin.system.service.SysDictService;
import cn.daenx.myadmin.system.vo.SysDictAddVo;
import cn.daenx.myadmin.system.vo.SysDictPageVo;
import cn.daenx.myadmin.system.vo.SysDictUpdVo;
import cn.daenx.myadmin.test.vo.TestDataPageVo;
import cn.daenx.myadmin.test.vo.TestDataUpdVo;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/dict")
public class SysDictController {
    @Resource
    private SysDictService sysDictService;

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:dict:list")
    @GetMapping(value = "/list")
    public Result list(SysDictPageVo vo) {
        IPage<SysDict> page = sysDictService.getPage(vo);
        return Result.ok(page);
    }

    /**
     * 导出
     */
    @SaCheckPermission("system:dict:export")
    @PostMapping("/export")
    public void export(SysDictPageVo vo, HttpServletResponse response) {
        List<SysDict> list = sysDictService.getAll(vo);
        ExcelUtil.exportXlsx(response, "字典", "字典编码", list, SysDict.class);
    }


    /**
     * 新增
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:dict:add")
    @PostMapping
    public Result add(@Validated @RequestBody SysDictAddVo vo) {
        sysDictService.addInfo(vo);
        return Result.ok();
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @SaCheckPermission("system:dict:query")
    @GetMapping(value = "/{id}")
    public Result query(@PathVariable String id) {
        SysDict sysDict = sysDictService.getInfo(id);
        return Result.ok(sysDict);
    }

    /**
     * 修改
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:dict:edit")
    @PutMapping
    public Result edit(@Validated @RequestBody SysDictUpdVo vo) {
        sysDictService.editInfo(vo);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission("system:dict:remove")
    @DeleteMapping("/{ids}")
    public Result remove(@PathVariable String[] ids) {
        sysDictService.deleteByIds(ids);
        return Result.ok();
    }

}
