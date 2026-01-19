package cn.daenx.server.api.admin.system;

import cn.daenx.framework.common.domain.vo.Result;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.excel.utils.ExcelUtil;
import cn.daenx.modules.system.domain.dto.sysDict.SysDictDetailAddDto;
import cn.daenx.modules.system.domain.dto.sysDict.SysDictDetailPageDto;
import cn.daenx.modules.system.domain.dto.sysDict.SysDictDetailUpdDto;
import cn.daenx.modules.system.domain.po.SysDictDetail;
import cn.daenx.modules.system.service.SysDictDetailService;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典明细管理
 */
@RestController
@RequestMapping("/system/dict/detail")
public class SysDictDetailController {
    @Resource
    private SysDictDetailService sysDictDetailService;

    /**
     * 分页列表
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("system:dict:page")
    @GetMapping(value = "/page")
    public Result<IPage<SysDictDetail>> page(SysDictDetailPageDto dto) {
        IPage<SysDictDetail> page = sysDictDetailService.getPage(dto);
        return Result.ok(page);
    }

    /**
     * 导出
     */
    @SaCheckPermission("system:dict:export")
    @PostMapping("/exportData")
    public void exportData(SysDictDetailPageDto dto, HttpServletResponse response) {
        List<SysDictDetail> list = sysDictDetailService.getAll(dto);
        ExcelUtil.exportXlsx(response, "字典明细", dto.getDictCode() + "字典明细", list, SysDictDetail.class);
    }


    /**
     * 新增
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("system:dict:add")
    @PostMapping("/add")
    public Result<Void> add(@Validated @RequestBody SysDictDetailAddDto dto) {
        sysDictDetailService.addInfo(dto);
        return Result.ok();
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @SaCheckPermission("system:dict:query")
    @GetMapping(value = "/query")
    public Result<SysDictDetail> query(@RequestParam(name = "id", required = true) String id) {
        SysDictDetail sysDictDetail = sysDictDetailService.getInfo(id);
        return Result.ok(sysDictDetail);
    }

    /**
     * 修改
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("system:dict:edit")
    @PostMapping(value = "/edit")
    public Result<Void> edit(@Validated @RequestBody SysDictDetailUpdDto dto) {
        sysDictDetailService.editInfo(dto);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission("system:dict:del")
    @PostMapping(value = "/del")
    public Result<Void> del(@RequestBody List<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            throw new MyException("参数错误");
        }
        sysDictDetailService.deleteByIds(ids);
        return Result.ok();
    }
}
