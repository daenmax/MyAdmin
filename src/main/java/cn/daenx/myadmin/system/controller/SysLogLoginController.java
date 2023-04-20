package cn.daenx.myadmin.system.controller;

import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.utils.ExcelUtil;
import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.system.po.SysLogLogin;
import cn.daenx.myadmin.system.service.SysLogLoginService;
import cn.daenx.myadmin.system.vo.SysLogLoginPageVo;
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
    @SaCheckPermission("monitor:logLogin:list")
    @GetMapping(value = "/list")
    public Result list(SysLogLoginPageVo vo) {
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
    @DeleteMapping()
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
    @DeleteMapping("/clean")
    public Result clean() {
        sysLogLoginService.clean();
        return Result.ok();
    }


}
