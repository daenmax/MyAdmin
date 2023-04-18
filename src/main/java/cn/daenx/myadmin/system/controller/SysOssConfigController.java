package cn.daenx.myadmin.system.controller;

import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.system.po.SysOssConfig;
import cn.daenx.myadmin.system.service.SysOssConfigService;
import cn.daenx.myadmin.system.vo.SysOssConfigPageVo;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/system/ossConfig")
public class SysOssConfigController {
    @Resource
    private SysOssConfigService sysOssConfigService;

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:ossConfig:list")
    @GetMapping(value = "/list")
    public Result list(SysOssConfigPageVo vo) {
        IPage<SysOssConfig> page = sysOssConfigService.getPage(vo);
        return Result.ok(page);
    }
    /**
     * 获取所有列表
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:ossConfig:list")
    @GetMapping(value = "/allList")
    public Result allList(SysOssConfigPageVo vo) {
        List<SysOssConfig> list = sysOssConfigService.getAll(vo);
        return Result.ok(list);
    }


}
