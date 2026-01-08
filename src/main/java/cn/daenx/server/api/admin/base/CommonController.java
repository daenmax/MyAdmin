package cn.daenx.server.api.admin.base;

import cn.daenx.framework.common.vo.Result;
import cn.daenx.data.system.domain.po.SysDictDetail;
import cn.daenx.data.system.service.SysConfigService;
import cn.daenx.data.system.service.SysDictDetailService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/common")
public class CommonController {

    @Resource
    private SysDictDetailService sysDictDetailService;
    @Resource
    private SysConfigService sysConfigService;

    /**
     * 根据字典编码查询字典详细信息
     *
     * @param dictCode 字典编码
     */
    @GetMapping(value = "/dict/{dictCode}")
    public Result dictType(@PathVariable String dictCode) {
        List<SysDictDetail> data = sysDictDetailService.getDictDetailByCodeFromRedis(dictCode);
        return Result.ok(data);
    }

    /**
     * 根据参数键名查询参数键值
     * 如果参数键名不存在或者未查询到，data返回null
     * 如果参数被禁用了，data返回空字符串""
     * 可根据此区别来进行你的业务逻辑
     *
     * @param key 参数键名
     */
    @GetMapping(value = "/config/{key}")
    public Result getConfigByKey(@PathVariable String key) {
        String value = sysConfigService.getConfigByKey(key);
        return Result.ok(value);
    }
}
