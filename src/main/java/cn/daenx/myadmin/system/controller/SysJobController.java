package cn.daenx.myadmin.system.controller;

import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.vo.ComIdVo;
import cn.daenx.myadmin.common.vo.ComStatusUpdVo;
import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.system.domain.po.SysJob;
import cn.daenx.myadmin.system.service.SysJobService;
import cn.daenx.myadmin.system.domain.vo.SysJobAddVo;
import cn.daenx.myadmin.system.domain.vo.SysJobPageVo;
import cn.daenx.myadmin.system.domain.vo.SysJobUpdVo;
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
    @SaCheckPermission("monitor:job:list")
    @GetMapping(value = "/list")
    public Result list(SysJobPageVo vo) {
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
    @GetMapping(value = "/{id}")
    public Result query(@PathVariable String id) {
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
    @PostMapping
    public Result add(@Validated @RequestBody SysJobAddVo vo) {
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
    @PutMapping
    public Result edit(@Validated @RequestBody SysJobUpdVo vo) {
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
    @PutMapping("/changeStatus")
    public Result changeStatus(@Validated @RequestBody ComStatusUpdVo vo) {
        sysJobService.changeStatus(vo);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission("monitor:job:remove")
    @DeleteMapping()
    public Result remove(@RequestBody List<String> ids) {
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
    public Result run(@Validated @RequestBody ComIdVo vo) {
        sysJobService.run(vo.getId());
        return Result.ok();
    }
}
