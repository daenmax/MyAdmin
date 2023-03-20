package cn.daenx.myadmin.system.controller;

import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.system.service.SysLoginService;
import cn.daenx.myadmin.system.service.SysUserService;
import cn.daenx.myadmin.system.vo.RouterVo;
import cn.daenx.myadmin.system.vo.SysLoginVo;
import cn.daenx.myadmin.system.vo.SysRegisterVo;
import cn.dev33.satoken.annotation.SaIgnore;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/user")
public class SysUserController {
    @Resource
    private SysUserService sysUserService;

    /**
     * 个人信息
     *
     * @return
     */
    @GetMapping("/profile")
    public Result profile() {
        Map<String, Object> map = sysUserService.profile();
        return Result.ok(map);
    }

}
