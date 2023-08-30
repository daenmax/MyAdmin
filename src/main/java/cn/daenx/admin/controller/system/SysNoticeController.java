package cn.daenx.admin.controller.system;

import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.vo.Result;
import cn.daenx.system.domain.dto.SysNoticePageDto;
import cn.daenx.system.domain.po.SysNotice;
import cn.daenx.system.domain.vo.SysNoticeAddVo;
import cn.daenx.system.domain.vo.SysNoticePageVo;
import cn.daenx.system.domain.vo.SysNoticeUpdVo;
import cn.daenx.system.service.SysNoticeService;
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
    @PostMapping("/add")
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
    @PostMapping("/edit")
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
    @PostMapping("/remove")
    public Result remove(@RequestBody List<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            throw new MyException("参数错误");
        }
        sysNoticeService.deleteByIds(ids);
        return Result.ok();
    }
}
