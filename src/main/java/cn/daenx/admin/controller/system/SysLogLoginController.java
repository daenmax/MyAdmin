package cn.daenx.admin.controller.system;

import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.vo.Result;
import cn.daenx.framework.excel.utils.ExcelUtil;
import cn.daenx.system.domain.po.SysLogLogin;
import cn.daenx.system.domain.vo.SysLogLoginPageVo;
import cn.daenx.system.service.SysLogLoginService;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/monitor/logLogin")
public class SysLogLoginController {
    @Resource
    private SysLogLoginService sysLogLoginService;

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("monitor:logLogin:page")
    @GetMapping(value = "/page")
    public Result page(SysLogLoginPageVo vo) {
        IPage<SysLogLogin> page = sysLogLoginService.getPage(vo);
        return Result.ok(page);
    }

    /**
     * 导出
     */
    @SaCheckPermission("monitor:logLogin:export")
    @PostMapping("/export")
    public void export(SysLogLoginPageVo vo, HttpServletResponse response) {
        List<SysLogLogin> list = sysLogLoginService.getAll(vo);
        ExcelUtil.exportXlsx(response, "登录日志", "登录日志", list, SysLogLogin.class);
    }


    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission("monitor:logLogin:remove")
    @PostMapping("/remove")
    public Result remove(@RequestBody List<String> ids) {
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
    @SaCheckPermission("monitor:logLogin:remove")
    @PostMapping("/clean")
    public Result clean() {
        sysLogLoginService.clean();
        return Result.ok();
    }


}
