package cn.daenx.server.api.admin.monitor;

import cn.daenx.framework.common.domain.vo.Result;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.excel.utils.ExcelUtil;
import cn.daenx.modules.system.domain.dto.sysLog.SysLogLoginPageDto;
import cn.daenx.modules.system.domain.po.SysLogLogin;
import cn.daenx.modules.system.service.SysLogLoginService;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 登录日志
 */
@RestController
@RequestMapping("/monitor/logLogin")
public class SysLogLoginController {
    @Resource
    private SysLogLoginService sysLogLoginService;

    /**
     * 分页列表
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("monitor:logLogin:page")
    @GetMapping(value = "/page")
    public Result<IPage<SysLogLogin>> page(SysLogLoginPageDto dto) {
        IPage<SysLogLogin> page = sysLogLoginService.getPage(dto);
        return Result.ok(page);
    }

    /**
     * 导出
     */
    @SaCheckPermission("monitor:logLogin:export")
    @PostMapping("/exportData")
    public void exportData(SysLogLoginPageDto dto, HttpServletResponse response) {
        List<SysLogLogin> list = sysLogLoginService.getAll(dto);
        ExcelUtil.exportXlsx(response, "登录日志", "登录日志", list, SysLogLogin.class);
    }


    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission("monitor:logLogin:del")
    @GetMapping(value = "/del")
    public Result<Void> del(@RequestBody List<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            throw new MyException("参数错误");
        }
        sysLogLoginService.deleteByIds(ids);
        return Result.ok();
    }


    /**
     * 清空日志
     *
     * @return
     */
    @SaCheckPermission("monitor:logLogin:del")
    @PostMapping("/clean")
    public Result<Void> clean() {
        sysLogLoginService.clean();
        return Result.ok();
    }


}
