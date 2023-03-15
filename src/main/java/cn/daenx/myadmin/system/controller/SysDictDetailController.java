package cn.daenx.myadmin.system.controller;

import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.system.po.SysDictDetail;
import cn.daenx.myadmin.system.service.SysDictDetailService;
import cn.daenx.myadmin.system.vo.SysDictDetailPageVo;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/dict/detail")
public class SysDictDetailController {
    @Resource
    private SysDictDetailService sysDictDetailService;

    /**
     * 根据字典编码查询字典详细信息
     *
     * @param dictCode 字典编码
     */
    @SaIgnore
    @GetMapping(value = "/type/{dictCode}")
    public Result dictType(@PathVariable String dictCode) {
        List<SysDictDetail> data = sysDictDetailService.getDictDetailByCodeFromRedis(dictCode);
        return Result.ok(data);
    }

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:dict:list")
    @GetMapping(value = "/list")
    public Result list(SysDictDetailPageVo vo) {
        IPage<SysDictDetail> page = sysDictDetailService.getPage(vo);
        return Result.ok(page);
    }
}
