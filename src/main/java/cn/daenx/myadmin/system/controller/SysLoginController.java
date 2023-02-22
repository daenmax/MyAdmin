package cn.daenx.myadmin.system.controller;

import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.system.service.SysLoginService;
import cn.daenx.myadmin.system.vo.SysLoginVo;
import cn.daenx.myadmin.system.vo.SysRegisterVo;
import cn.daenx.myadmin.test.dto.TestDataPageDto;
import cn.daenx.myadmin.test.service.TestDataService;
import cn.daenx.myadmin.test.vo.TestDataPageVo;
import cn.dev33.satoken.annotation.SaIgnore;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("")
public class SysLoginController {
    @Resource
    private SysLoginService sysLoginService;

    /**
     * PC登录
     *
     * @param vo
     * @return
     */
    @SaIgnore
    @PostMapping("/login")
    public Result login(@RequestBody @Validated SysLoginVo vo) {
        String token = sysLoginService.login(vo);
        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        return Result.ok(map);
    }
    //APP登录
    //微信小程序登录
    //开放API登录

    /**
     * 通用注册接口
     * 只接受账号和密码
     * 手机号、邮箱、openid需要另外单独绑定
     *
     * @param vo
     * @return
     */
    @SaIgnore
    @PostMapping("/register")
    public Result register(@RequestBody @Validated SysRegisterVo vo) {
        sysLoginService.register(vo);
        return Result.ok();
    }

    /**
     * 绑定手机号
     */

    /**
     * 绑定邮箱
     */

    /**
     * 绑定openid
     */
}
