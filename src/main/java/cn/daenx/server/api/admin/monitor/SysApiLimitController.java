package cn.daenx.server.api.admin.monitor;

import cn.daenx.system.service.SysApiLimitService;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.vo.ComStatusUpdVo;
import cn.daenx.framework.common.vo.Result;
import cn.daenx.system.domain.po.SysApiLimit;
import cn.daenx.system.domain.vo.SysApiLimitAddVo;
import cn.daenx.system.domain.vo.SysApiLimitPageVo;
import cn.daenx.system.domain.vo.SysApiLimitUpdVo;
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
    @SaCheckPermission("monitor:apiLimit:page")
    @GetMapping(value = "/page")
    public Result page(SysApiLimitPageVo vo) {
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
    @GetMapping(value = "/query")
    public Result query(@RequestParam(name = "id", required = true) String id) {
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
    @PostMapping("/add")
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
    @PostMapping("/edit")
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
    @PostMapping("/changeStatus")
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
    @SaCheckPermission("monitor:apiLimit:del")
    @PostMapping("/del")
    public Result del(@RequestBody List<String> ids) {
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
    public Result refreshCache() {
        SysApiLimitService.refreshApiLimitCache();
        return Result.ok();
    }
}
