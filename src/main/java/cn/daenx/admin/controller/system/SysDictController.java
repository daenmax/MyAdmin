package cn.daenx.admin.controller.system;

import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.vo.Result;
import cn.daenx.framework.excel.utils.ExcelUtil;
import cn.daenx.system.domain.po.SysDict;
import cn.daenx.system.domain.vo.SysDictAddVo;
import cn.daenx.system.domain.vo.SysDictPageVo;
import cn.daenx.system.domain.vo.SysDictUpdVo;
import cn.daenx.system.service.SysDictService;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
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
    @PostMapping("/add")
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
    @PostMapping("/edit")
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
    @PostMapping("/remove")
    public Result remove(@RequestBody List<String> ids) {
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
    public Result refreshCache() {
        sysDictService.refreshCache();
        return Result.ok();
    }


    /**
     * 获取字典选择框列表
     *
     * @return
     */
    @GetMapping(value = "/optionSelect")
    public Result optionSelect() {
        List<SysDict> list = sysDictService.getAll(new SysDictPageVo());
        return Result.ok(list);
    }
}
