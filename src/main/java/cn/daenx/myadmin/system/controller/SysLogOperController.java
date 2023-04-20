package cn.daenx.myadmin.system.controller;

import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.utils.ExcelUtil;
import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.system.po.SysLogOper;
import cn.daenx.myadmin.system.service.SysLogOperService;
import cn.daenx.myadmin.system.vo.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/monitor/logOper")
public class SysLogOperController {
    @Resource
    private SysLogOperService sysLogOperService;

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("monitor:logOper:list")
    @GetMapping(value = "/list")
    public Result list(SysLogOperPageVo vo) {
        IPage<SysLogOper> page = sysLogOperService.getPage(vo);
        return Result.ok(page);
    }

    /**
     * 导出
     */
    @SaCheckPermission("monitor:logOper:export")
    @PostMapping("/export")
    public void export(SysLogOperPageVo vo, HttpServletResponse response) {
        List<SysLogOper> list = sysLogOperService.getAll(vo);
        ExcelUtil.exportXlsx(response, "操作日志", "操作日志", list, SysLogOper.class);
    }


    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission("monitor:logOper:remove")
    @DeleteMapping()
    public Result remove(@RequestBody List<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            throw new MyException("参数错误");
        }
        sysLogOperService.deleteByIds(ids);
        return Result.ok();
    }


    /**
     * 清空日志
     *
     * @return
     */
    @SaCheckPermission("monitor:logOper:remove")
    @DeleteMapping("/clean")
    public Result clean() {
        sysLogOperService.clean();
        return Result.ok();
    }


}
