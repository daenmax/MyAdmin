package cn.daenx.server.api.admin.monitor;

import cn.daenx.framework.common.domain.dto.ComStatusUpdDto;
import cn.daenx.framework.common.domain.vo.Result;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.modules.system.domain.dto.sysApiLimit.SysApiLimitAddDto;
import cn.daenx.modules.system.domain.dto.sysApiLimit.SysApiLimitPageDto;
import cn.daenx.modules.system.domain.dto.sysApiLimit.SysApiLimitUpdDto;
import cn.daenx.modules.system.domain.po.SysApiLimit;
import cn.daenx.modules.system.service.SysApiLimitService;
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
     * @param dto
     * @return
     */
    @SaCheckPermission("monitor:apiLimit:page")
    @GetMapping(value = "/page")
    public Result<IPage<SysApiLimit>> page(SysApiLimitPageDto dto) {
        IPage<SysApiLimit> page = SysApiLimitService.getPage(dto);
        return Result.ok(page);
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @SaCheckPermission("monitor:apiLimit:query")
    @GetMapping(value = "/query")
    public Result<SysApiLimit> query(@RequestParam(name = "id", required = true) String id) {
        SysApiLimit SysApiLimit = SysApiLimitService.getInfo(id);
        return Result.ok(SysApiLimit);
    }

    /**
     * 新增
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("monitor:apiLimit:add")
    @PostMapping("/add")
    public Result<Void> add(@Validated @RequestBody SysApiLimitAddDto dto) {
        SysApiLimitService.addInfo(dto);
        return Result.ok();
    }

    /**
     * 修改
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("monitor:apiLimit:edit")
    @PostMapping("/edit")
    public Result<Void> edit(@Validated @RequestBody SysApiLimitUpdDto dto) {
        SysApiLimitService.editInfo(dto);
        return Result.ok();
    }

    /**
     * 修改状态
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("monitor:apiLimit:edit")
    @PostMapping("/changeStatus")
    public Result<Void> changeStatus(@Validated @RequestBody ComStatusUpdDto dto) {
        SysApiLimitService.changeStatus(dto);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission("monitor:apiLimit:del")
    @PostMapping("/del")
    public Result<Void> del(@RequestBody List<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            throw new MyException("参数错误");
        }
        SysApiLimitService.deleteByIds(ids);
        return Result.ok();
    }

    /**
     * 刷新缓存
     *
     * @return
     */
    @SaCheckPermission("monitor:apiLimit:refreshCache")
    @PostMapping("/refreshCache")
    public Result<Void> refreshCache() {
        SysApiLimitService.refreshApiLimitCache();
        return Result.ok();
    }
}
