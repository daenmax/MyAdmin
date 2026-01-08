package cn.daenx.server.api.admin.monitor;

import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.domain.dto.ComIdDto;
import cn.daenx.framework.common.domain.dto.ComStatusUpdDto;
import cn.daenx.framework.common.domain.vo.Result;
import cn.daenx.modules.system.domain.po.SysJob;
import cn.daenx.modules.system.domain.dto.sysJob.SysJobAddDto;
import cn.daenx.modules.system.domain.dto.sysJob.SysJobPageDto;
import cn.daenx.modules.system.domain.dto.sysJob.SysJobUpdDto;
import cn.daenx.modules.system.service.SysJobService;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/monitor/job")
public class SysJobController {
    @Resource
    private SysJobService sysJobService;

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("monitor:job:page")
    @GetMapping(value = "/page")
    public Result page(SysJobPageDto vo) {
        IPage<SysJob> page = sysJobService.getPage(vo);
        return Result.ok(page);
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @SaCheckPermission("monitor:job:query")
    @GetMapping(value = "/query")
    public Result query(@RequestParam(name = "id", required = true) String id) {
        SysJob sysJob = sysJobService.getInfo(id);
        return Result.ok(sysJob);
    }

    /**
     * 新增
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("monitor:job:add")
    @PostMapping("/add")
    public Result add(@Validated @RequestBody SysJobAddDto vo) {
        sysJobService.addInfo(vo);
        return Result.ok();
    }

    /**
     * 修改
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("monitor:job:edit")
    @PostMapping("/edit")
    public Result edit(@Validated @RequestBody SysJobUpdDto vo) {
        sysJobService.editInfo(vo);
        return Result.ok();
    }

    /**
     * 修改状态
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("monitor:job:edit")
    @PostMapping("/changeStatus")
    public Result changeStatus(@Validated @RequestBody ComStatusUpdDto vo) {
        sysJobService.changeStatus(vo);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission("monitor:job:del")
    @PostMapping("/del")
    public Result del(@RequestBody List<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            throw new MyException("参数错误");
        }
        sysJobService.deleteByIds(ids);
        return Result.ok();
    }

    /**
     * 定时任务立即执行一次
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("monitor:job:run")
    @PostMapping("/run")
    public Result run(@Validated @RequestBody ComIdDto vo) {
        sysJobService.run(vo.getId());
        return Result.ok();
    }
}
