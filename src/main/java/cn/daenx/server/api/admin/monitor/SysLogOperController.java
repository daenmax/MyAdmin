package cn.daenx.server.api.admin.monitor;

import cn.daenx.framework.common.domain.vo.Result;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.excel.utils.ExcelUtil;
import cn.daenx.modules.system.domain.dto.sysLog.SysLogOperPageDto;
import cn.daenx.modules.system.domain.po.SysLogOper;
import cn.daenx.modules.system.service.SysLogOperService;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 操作日志
 */
@RestController
@RequestMapping("/monitor/logOper")
public class SysLogOperController {
    @Resource
    private SysLogOperService sysLogOperService;

    /**
     * 分页列表
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("monitor:logOper:page")
    @GetMapping(value = "/page")
    public Result<IPage<SysLogOper>> page(SysLogOperPageDto dto) {
        IPage<SysLogOper> page = sysLogOperService.getPage(dto);
        return Result.ok(page);
    }

    /**
     * 导出
     */
    @SaCheckPermission("monitor:logOper:export")
    @PostMapping("/exportData")
    public void exportData(SysLogOperPageDto dto, HttpServletResponse response) {
        List<SysLogOper> list = sysLogOperService.getAll(dto);
        ExcelUtil.exportXlsx(response, "操作日志", "操作日志", list, SysLogOper.class);
    }


    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission("monitor:logOper:del")
    @PostMapping(value = "/del")
    public Result<Void> del(@RequestBody List<String> ids) {
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
    @SaCheckPermission("monitor:logOper:del")
    @PostMapping("/clean")
    public Result<Void> clean() {
        sysLogOperService.clean();
        return Result.ok();
    }


}
