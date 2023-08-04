package cn.daenx.web.controller.system;

import cn.daenx.common.exception.MyException;
import cn.daenx.common.vo.ComStatusUpdVo;
import cn.daenx.common.vo.Result;
import cn.daenx.system.domain.po.SysOssConfig;
import cn.daenx.system.domain.vo.SysOssConfigAddVo;
import cn.daenx.system.domain.vo.SysOssConfigPageVo;
import cn.daenx.system.domain.vo.SysOssConfigUpdVo;
import cn.daenx.system.service.SysOssConfigService;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/monitor/ossConfig")
public class SysOssConfigController {
    @Resource
    private SysOssConfigService sysOssConfigService;

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("monitor:ossConfig:list")
    @GetMapping(value = "/list")
    public Result list(SysOssConfigPageVo vo) {
        IPage<SysOssConfig> page = sysOssConfigService.getPage(vo);
        return Result.ok(page);
    }
    /**
     * 获取所有列表
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("monitor:ossConfig:list")
    @GetMapping(value = "/allList")
    public Result allList(SysOssConfigPageVo vo) {
        List<SysOssConfig> list = sysOssConfigService.getAll(vo);
        return Result.ok(list);
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @SaCheckPermission("monitor:ossConfig:query")
    @GetMapping(value = "/{id}")
    public Result query(@PathVariable String id) {
        SysOssConfig sysOssConfig = sysOssConfigService.getInfo(id);
        return Result.ok(sysOssConfig);
    }

    /**
     * 修改
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("monitor:ossConfig:edit")
    @PostMapping("/edit")
    public Result edit(@Validated @RequestBody SysOssConfigUpdVo vo) {
        sysOssConfigService.editInfo(vo);
        return Result.ok();
    }

    /**
     * 新增
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("monitor:ossConfig:add")
    @PostMapping("/add")
    public Result add(@Validated @RequestBody SysOssConfigAddVo vo) {
        sysOssConfigService.addInfo(vo);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission("monitor:ossConfig:remove")
    @PostMapping("/remove")
    public Result remove(@RequestBody List<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            throw new MyException("参数错误");
        }
        sysOssConfigService.deleteByIds(ids);
        return Result.ok();
    }

    /**
     * 修改配置状态
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("monitor:ossConfig:edit")
    @PostMapping("/changeStatus")
    public Result changeStatus(@Validated @RequestBody ComStatusUpdVo vo) {
        sysOssConfigService.changeStatus(vo);
        return Result.ok();
    }

    /**
     * 修改使用状态
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("monitor:ossConfig:edit")
    @PostMapping("/changeInUse")
    public Result changeInUse(@Validated @RequestBody ComStatusUpdVo vo) {
        sysOssConfigService.changeInUse(vo);
        return Result.ok();
    }

}
