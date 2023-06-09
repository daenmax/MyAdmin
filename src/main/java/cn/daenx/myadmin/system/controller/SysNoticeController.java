package cn.daenx.myadmin.system.controller;

import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.system.domain.dto.SysNoticePageDto;
import cn.daenx.myadmin.system.domain.po.SysNotice;
import cn.daenx.myadmin.system.service.SysNoticeService;
import cn.daenx.myadmin.system.domain.vo.SysNoticeAddVo;
import cn.daenx.myadmin.system.domain.vo.SysNoticePageVo;
import cn.daenx.myadmin.system.domain.vo.SysNoticeUpdVo;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monitor/notice")
public class SysNoticeController {
    @Resource
    private SysNoticeService sysNoticeService;

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("monitor:notice:list")
    @GetMapping("/list")
    public Result list(SysNoticePageVo vo) {
        IPage<SysNoticePageDto> page = sysNoticeService.getPage(vo);
        return Result.ok(page);
    }

    /**
     * 新增
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("monitor:notice:add")
    @PostMapping
    public Result add(@Validated @RequestBody SysNoticeAddVo vo) {
        sysNoticeService.addInfo(vo);
        return Result.ok();
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @SaCheckPermission("monitor:notice:query")
    @GetMapping(value = "/{id}")
    public Result query(@PathVariable String id) {
        SysNotice sysNotice = sysNoticeService.getInfo(id);
        return Result.ok(sysNotice);
    }

    /**
     * 修改
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("monitor:notice:edit")
    @PutMapping
    public Result edit(@Validated @RequestBody SysNoticeUpdVo vo) {
        sysNoticeService.editInfo(vo);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission("monitor:notice:remove")
    @DeleteMapping()
    public Result remove(@RequestBody List<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            throw new MyException("参数错误");
        }
        sysNoticeService.deleteByIds(ids);
        return Result.ok();
    }
}
