package cn.daenx.server.api.admin.monitor;

import cn.daenx.framework.common.domain.vo.Result;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.modules.system.domain.dto.sysNotice.SysNoticeAddDto;
import cn.daenx.modules.system.domain.dto.sysNotice.SysNoticePageDto;
import cn.daenx.modules.system.domain.dto.sysNotice.SysNoticeUpdDto;
import cn.daenx.modules.system.domain.po.SysNotice;
import cn.daenx.modules.system.domain.vo.sysNotice.SysNoticePageVo;
import cn.daenx.modules.system.service.SysNoticeService;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 通知公告
 */
@RestController
@RequestMapping("/monitor/notice")
public class SysNoticeController {
    @Resource
    private SysNoticeService sysNoticeService;

    /**
     * 分页列表
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("monitor:notice:page")
    @GetMapping("/page")
    public Result<IPage<SysNoticePageVo>> page(SysNoticePageDto dto) {
        IPage<SysNoticePageVo> page = sysNoticeService.getPage(dto);
        return Result.ok(page);
    }

    /**
     * 新增
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("monitor:notice:add")
    @PostMapping("/add")
    public Result<Void> add(@Validated @RequestBody SysNoticeAddDto dto) {
        sysNoticeService.addInfo(dto);
        return Result.ok();
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @SaCheckPermission("monitor:notice:query")
    @GetMapping(value = "/query")
    public Result<SysNotice> query(@RequestParam(name = "id", required = true) String id) {
        SysNotice sysNotice = sysNoticeService.getInfo(id);
        return Result.ok(sysNotice);
    }

    /**
     * 修改
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("monitor:notice:edit")
    @PostMapping(value = "/edit")
    public Result<Void> edit(@Validated @RequestBody SysNoticeUpdDto dto) {
        sysNoticeService.editInfo(dto);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission("monitor:notice:del")
    @PostMapping(value = "/del")
    public Result<Void> del(@RequestBody List<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            throw new MyException("参数错误");
        }
        sysNoticeService.deleteByIds(ids);
        return Result.ok();
    }
}
