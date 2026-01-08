package cn.daenx.server.api.admin.system;

import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.vo.Result;
import cn.daenx.framework.excel.utils.ExcelUtil;
import cn.daenx.data.system.domain.po.SysConfig;
import cn.daenx.data.system.domain.vo.SysConfigAddVo;
import cn.daenx.data.system.domain.vo.SysConfigPageVo;
import cn.daenx.data.system.domain.vo.SysConfigUpdVo;
import cn.daenx.data.system.service.SysConfigService;
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
    @SaCheckPermission("system:config:page")
    @GetMapping(value = "/page")
    public Result page(SysConfigPageVo vo) {
        IPage<SysConfig> page = sysConfigService.getPage(vo);
        return Result.ok(page);
    }

    /**
     * 导出
     */
    @SaCheckPermission("system:config:export")
    @PostMapping("/exportData")
    public void exportData(SysConfigPageVo vo, HttpServletResponse response) {
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
    @GetMapping(value = "/query")
    public Result query(@RequestParam(name = "id", required = true) String id) {
        SysConfig sysConfig = sysConfigService.getInfo(id);
        return Result.ok(sysConfig);
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
    @SaCheckPermission("system:config:del")
    @PostMapping("/del")
    public Result del(@RequestBody List<String> ids) {
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
