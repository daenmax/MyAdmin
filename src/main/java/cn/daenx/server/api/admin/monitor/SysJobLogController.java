package cn.daenx.server.api.admin.monitor;

import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.vo.Result;
import cn.daenx.framework.excel.utils.ExcelUtil;
import cn.daenx.system.domain.dto.SysJobLogPageDto;
import cn.daenx.system.domain.vo.SysJobLogPageVo;
import cn.daenx.system.service.SysJobLogService;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/monitor/jobLog")
public class SysJobLogController {
    @Resource
    private SysJobLogService sysJobLogService;

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("monitor:jobLog:page")
    @GetMapping(value = "/page")
    public Result page(SysJobLogPageVo vo) {
        IPage<SysJobLogPageDto> page = sysJobLogService.getPage(vo);
        return Result.ok(page);
    }

    /**
     * 导出
     *
     * @param vo
     * @param response
     */
    @SaCheckPermission("monitor:jobLog:export")
    @PostMapping("/exportData")
    public void exportData(SysJobLogPageVo vo, HttpServletResponse response) {
        List<SysJobLogPageDto> list = sysJobLogService.getAll(vo);
        ExcelUtil.exportXlsx(response, "定时任务执行日志", "定时任务执行日志", list, SysJobLogPageDto.class);
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @SaCheckPermission("monitor:jobLog:query")
    @GetMapping(value = "/query")
    public Result query(@RequestParam(name = "id", required = true) String id) {
        SysJobLogPageDto sysJobLogPageDto = sysJobLogService.getInfo(id);
        return Result.ok(sysJobLogPageDto);
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission("monitor:jobLog:del")
    @PostMapping("/del")
    public Result del(@RequestBody List<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            throw new MyException("参数错误");
        }
        sysJobLogService.deleteByIds(ids);
        return Result.ok();
    }

    /**
     * 清空日志
     *
     * @return
     */
    @SaCheckPermission("monitor:jobLog:del")
    @PostMapping("/clean")
    public Result clean() {
        sysJobLogService.clean();
        return Result.ok();
    }

}
