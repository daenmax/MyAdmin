package cn.daenx.server.api.admin.system;

import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.vo.Result;
import cn.daenx.framework.excel.utils.ExcelUtil;
import cn.daenx.system.domain.po.SysDictDetail;
import cn.daenx.system.domain.vo.SysDictDetailAddVo;
import cn.daenx.system.domain.vo.SysDictDetailPageVo;
import cn.daenx.system.domain.vo.SysDictDetailUpdVo;
import cn.daenx.system.service.SysDictDetailService;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/dict/detail")
public class SysDictDetailController {
    @Resource
    private SysDictDetailService sysDictDetailService;

    /**
     * 根据字典编码查询字典详细信息
     *
     * @param dictCode 字典编码
     */
    @GetMapping(value = "/type/{dictCode}")
    public Result dictType(@PathVariable String dictCode) {
        List<SysDictDetail> data = sysDictDetailService.getDictDetailByCodeFromRedis(dictCode);
        return Result.ok(data);
    }

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:dict:page")
    @GetMapping(value = "/page")
    public Result page(SysDictDetailPageVo vo) {
        IPage<SysDictDetail> page = sysDictDetailService.getPage(vo);
        return Result.ok(page);
    }

    /**
     * 导出
     */
    @SaCheckPermission("system:dict:export")
    @PostMapping("/export")
    public void export(SysDictDetailPageVo vo, HttpServletResponse response) {
        List<SysDictDetail> list = sysDictDetailService.getAll(vo);
        ExcelUtil.exportXlsx(response, "字典明细", vo.getDictCode() + "字典明细", list, SysDictDetail.class);
    }


    /**
     * 新增
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:dict:add")
    @PostMapping("/add")
    public Result add(@Validated @RequestBody SysDictDetailAddVo vo) {
        sysDictDetailService.addInfo(vo);
        return Result.ok();
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @SaCheckPermission("system:dict:query")
    @GetMapping(value = "/{id}")
    public Result query(@PathVariable String id) {
        SysDictDetail sysDictDetail = sysDictDetailService.getInfo(id);
        return Result.ok(sysDictDetail);
    }

    /**
     * 修改
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:dict:edit")
    @PostMapping("/edit")
    public Result edit(@Validated @RequestBody SysDictDetailUpdVo vo) {
        sysDictDetailService.editInfo(vo);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission("system:dict:remove")
    @PostMapping("/remove")
    public Result remove(@RequestBody List<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            throw new MyException("参数错误");
        }
        sysDictDetailService.deleteByIds(ids);
        return Result.ok();
    }
}
