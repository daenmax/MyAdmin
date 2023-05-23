package cn.daenx.myadmin.system.controller;

import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.vo.ComStatusUpdVo;
import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.system.po.SysApiLimit;
import cn.daenx.myadmin.system.service.SysApiLimitService;
import cn.daenx.myadmin.system.vo.SysApiLimitAddVo;
import cn.daenx.myadmin.system.vo.SysApiLimitPageVo;
import cn.daenx.myadmin.system.vo.SysApiLimitUpdVo;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/monitor/apiLimit")
public class SysApiLimitController {
    @Resource
    private SysApiLimitService SysApiLimitService;

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("monitor:apiLimit:list")
    @GetMapping(value = "/list")
    public Result list(SysApiLimitPageVo vo) {
        IPage<SysApiLimit> page = SysApiLimitService.getPage(vo);
        return Result.ok(page);
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @SaCheckPermission("monitor:apiLimit:query")
    @GetMapping(value = "/{id}")
    public Result query(@PathVariable String id) {
        SysApiLimit SysApiLimit = SysApiLimitService.getInfo(id);
        return Result.ok(SysApiLimit);
    }

    /**
     * 新增
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("monitor:apiLimit:add")
    @PostMapping
    public Result add(@Validated @RequestBody SysApiLimitAddVo vo) {
        SysApiLimitService.addInfo(vo);
        return Result.ok();
    }

    /**
     * 修改
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("monitor:apiLimit:edit")
    @PutMapping
    public Result edit(@Validated @RequestBody SysApiLimitUpdVo vo) {
        SysApiLimitService.editInfo(vo);
        return Result.ok();
    }

    /**
     * 修改状态
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("monitor:apiLimit:edit")
    @PutMapping("/changeStatus")
    public Result changeStatus(@Validated @RequestBody ComStatusUpdVo vo) {
        SysApiLimitService.changeStatus(vo);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission("monitor:apiLimit:remove")
    @DeleteMapping()
    public Result remove(@RequestBody List<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            throw new MyException("参数错误");
        }
        SysApiLimitService.deleteByIds(ids);
        return Result.ok();
    }
}
