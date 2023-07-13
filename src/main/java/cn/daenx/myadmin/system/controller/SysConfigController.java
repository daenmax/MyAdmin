package cn.daenx.myadmin.system.controller;

import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.framework.excel.utils.ExcelUtil;
import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.system.domain.po.SysConfig;
import cn.daenx.myadmin.system.domain.vo.SysConfigAddVo;
import cn.daenx.myadmin.system.domain.vo.SysConfigPageVo;
import cn.daenx.myadmin.system.domain.vo.SysConfigUpdVo;
import cn.daenx.myadmin.system.service.SysConfigService;
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
     * @param vo
     * @return
     */
    @SaCheckPermission("system:config:list")
    @GetMapping(value = "/list")
    public Result list(SysConfigPageVo vo) {
        IPage<SysConfig> page = sysConfigService.getPage(vo);
        return Result.ok(page);
    }

    /**
     * 导出
     */
    @SaCheckPermission("system:config:export")
    @PostMapping("/export")
    public void export(SysConfigPageVo vo, HttpServletResponse response) {
        List<SysConfig> list = sysConfigService.getAll(vo);
        ExcelUtil.exportXlsx(response, "系统参数", "系统参数", list, SysConfig.class);
    }


    /**
     * 查询
     *
     * @param id
     * @return
     */
    @SaCheckPermission("system:config:query")
    @GetMapping(value = "/{id}")
    public Result query(@PathVariable String id) {
        SysConfig sysConfig = sysConfigService.getInfo(id);
        return Result.ok(sysConfig);
    }

    /**
     * 根据参数键名查询参数键值
     * 如果参数键名不存在或者未查询到，data返回null
     * 如果参数被禁用了，data返回空字符串""
     * 可根据此区别来进行你的业务逻辑
     *
     * @param key 参数键名
     */
    @GetMapping(value = "/key/{key}")
    public Result getConfigByKey(@PathVariable String key) {
        String value = sysConfigService.getConfigByKey(key);
        return Result.ok(value);
    }

    /**
     * 修改
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:config:edit")
    @PostMapping("/edit")
    public Result edit(@Validated @RequestBody SysConfigUpdVo vo) {
        sysConfigService.editInfo(vo);
        return Result.ok();
    }

    /**
     * 新增
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:config:add")
    @PostMapping("/add")
    public Result add(@Validated @RequestBody SysConfigAddVo vo) {
        sysConfigService.addInfo(vo);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission("system:config:remove")
    @PostMapping("/remove")
    public Result remove(@RequestBody List<String> ids) {
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
    public Result refreshCache() {
        sysConfigService.refreshCache();
        return Result.ok();
    }

}
