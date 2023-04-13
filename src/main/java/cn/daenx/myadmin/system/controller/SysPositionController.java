package cn.daenx.myadmin.system.controller;

import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.utils.ExcelUtil;
import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.system.po.SysDict;
import cn.daenx.myadmin.system.po.SysPosition;
import cn.daenx.myadmin.system.service.SysPositionService;
import cn.daenx.myadmin.system.vo.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/system/position")
public class SysPositionController {
    @Resource
    private SysPositionService sysPositionService;

    /**
     * 列表
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:position:list")
    @GetMapping(value = "/list")
    public Result list(SysPositionPageVo vo) {
        IPage<SysPosition> page = sysPositionService.getPage(vo);
        return Result.ok(page);
    }

    /**
     * 导出
     */
    @SaCheckPermission("system:position:export")
    @PostMapping("/export")
    public void export(SysPositionPageVo vo, HttpServletResponse response) {
        List<SysPosition> list = sysPositionService.getAll(vo);
        ExcelUtil.exportXlsx(response, "岗位", "岗位", list, SysPosition.class);
    }


    /**
     * 查询
     *
     * @param id
     * @return
     */
    @SaCheckPermission("system:position:query")
    @GetMapping(value = "/{id}")
    public Result query(@PathVariable String id) {
        SysPosition sysPosition = sysPositionService.getInfo(id);
        return Result.ok(sysPosition);
    }

    /**
     * 修改
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:position:edit")
    @PutMapping
    public Result edit(@Validated @RequestBody SysPositionUpdVo vo) {
        sysPositionService.editInfo(vo);
        return Result.ok();
    }

    /**
     * 新增
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:position:add")
    @PostMapping
    public Result add(@Validated @RequestBody SysPositionAddVo vo) {
        sysPositionService.addInfo(vo);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission("system:position:remove")
    @DeleteMapping()
    public Result remove(@RequestBody List<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            throw new MyException("参数错误");
        }
        sysPositionService.deleteByIds(ids);
        return Result.ok();
    }

}