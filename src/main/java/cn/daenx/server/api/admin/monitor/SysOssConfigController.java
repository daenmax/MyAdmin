package cn.daenx.server.api.admin.monitor;

import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.domain.dto.ComStatusUpdDto;
import cn.daenx.framework.common.domain.vo.Result;
import cn.daenx.modules.system.domain.po.SysOssConfig;
import cn.daenx.modules.system.domain.dto.sysOss.SysOssConfigAddDto;
import cn.daenx.modules.system.domain.dto.sysOss.SysOssConfigPageDto;
import cn.daenx.modules.system.domain.dto.sysOss.SysOssConfigUpdDto;
import cn.daenx.modules.system.service.SysOssConfigService;
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
    @SaCheckPermission("monitor:ossConfig:page")
    @GetMapping(value = "/page")
    public Result page(SysOssConfigPageDto vo) {
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
    public Result allList(SysOssConfigPageDto vo) {
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
    @GetMapping(value = "/query")
    public Result query(@RequestParam(name = "id", required = true) String id) {
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
    public Result edit(@Validated @RequestBody SysOssConfigUpdDto vo) {
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
    public Result add(@Validated @RequestBody SysOssConfigAddDto vo) {
        sysOssConfigService.addInfo(vo);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission("monitor:ossConfig:del")
    @PostMapping("/del")
    public Result del(@RequestBody List<String> ids) {
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
    public Result changeStatus(@Validated @RequestBody ComStatusUpdDto vo) {
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
    public Result changeInUse(@Validated @RequestBody ComStatusUpdDto vo) {
        sysOssConfigService.changeInUse(vo);
        return Result.ok();
    }

}
