package cn.daenx.myadmin.system.controller;

import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.system.service.SysLoginService;
import cn.daenx.myadmin.system.vo.SysLoginVo;
import cn.daenx.myadmin.test.dto.TestDataPageDto;
import cn.daenx.myadmin.test.service.TestDataService;
import cn.daenx.myadmin.test.vo.TestDataPageVo;
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
     * 用户登录
     *
     * @param vo
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody @Validated SysLoginVo vo) {
        String token = sysLoginService.login(vo);
        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        return Result.ok(map);
    }

}
