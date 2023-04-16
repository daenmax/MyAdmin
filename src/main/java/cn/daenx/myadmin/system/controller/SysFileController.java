package cn.daenx.myadmin.system.controller;

import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.utils.ExcelUtil;
import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.system.dto.SysFilePageDto;
import cn.daenx.myadmin.system.po.SysConfig;
import cn.daenx.myadmin.system.service.SysConfigService;
import cn.daenx.myadmin.system.service.SysFileService;
import cn.daenx.myadmin.system.vo.SysConfigAddVo;
import cn.daenx.myadmin.system.vo.SysConfigPageVo;
import cn.daenx.myadmin.system.vo.SysConfigUpdVo;
import cn.daenx.myadmin.system.vo.SysFilePageVo;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/system/file")
public class SysFileController {
    @Resource
    private SysFileService sysFileService;

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:file:list")
    @GetMapping(value = "/list")
    public Result list(SysFilePageVo vo) {
        IPage<SysFilePageDto> page = sysFileService.getPage(vo);
        return Result.ok(page);
    }


    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission("system:file:remove")
    @DeleteMapping()
    public Result remove(@RequestBody List<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            throw new MyException("参数错误");
        }
        sysFileService.deleteByIds(ids);
        return Result.ok();
    }

}
