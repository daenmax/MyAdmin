package cn.daenx.myadmin.system.controller;

import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.system.po.SysDict;
import cn.daenx.myadmin.system.po.SysDictDetail;
import cn.daenx.myadmin.system.service.SysDictDetailService;
import cn.daenx.myadmin.system.service.SysDictService;
import cn.daenx.myadmin.system.vo.SysDictPageVo;
import cn.daenx.myadmin.test.vo.TestDataPageVo;
import cn.dev33.satoken.annotation.SaIgnore;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/dict")
public class SysDictController {
    @Resource
    private SysDictService sysDictService;


    @GetMapping(value = "/list")
    public Result list(SysDictPageVo vo) {
        IPage<SysDict> page = sysDictService.getPage(vo);
        return Result.ok(page);
    }
}
