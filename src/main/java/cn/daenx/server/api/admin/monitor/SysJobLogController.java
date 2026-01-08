package cn.daenx.server.api.admin.monitor;

import cn.daenx.modules.system.domain.vo.sysJob.SysJobLogPageVo;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.domain.vo.Result;
import cn.daenx.framework.excel.utils.ExcelUtil;
import cn.daenx.modules.system.domain.dto.sysJob.SysJobLogPageDto;
import cn.daenx.modules.system.service.SysJobLogService;
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
    public Result page(SysJobLogPageDto vo) {
        IPage<SysJobLogPageVo> page = sysJobLogService.getPage(vo);
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
    public void exportData(SysJobLogPageDto vo, HttpServletResponse response) {
        List<SysJobLogPageVo> list = sysJobLogService.getAll(vo);
        ExcelUtil.exportXlsx(response, "定时任务执行日志", "定时任务执行日志", list, SysJobLogPageVo.class);
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
        SysJobLogPageVo sysJobLogPageVo = sysJobLogService.getInfo(id);
        return Result.ok(sysJobLogPageVo);
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
