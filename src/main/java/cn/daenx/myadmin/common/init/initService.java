package cn.daenx.myadmin.common.init;

import cn.daenx.myadmin.common.constant.RedisConstant;
import cn.daenx.myadmin.common.utils.RedisUtil;
import cn.daenx.myadmin.system.po.SysDict;
import cn.daenx.myadmin.system.po.SysDictDetail;
import cn.daenx.myadmin.system.service.SysDictDetailService;
import cn.daenx.myadmin.system.service.SysDictService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 项目启动初始化代码
 */
@Configuration
public class initService {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private SysDictService sysDictService;
    @Resource
    private SysDictDetailService sysDictDetailService;

    /**
     * 初始化字典
     */
    @PostConstruct
    public void initDict() {
        redisUtil.delBatch(RedisConstant.DICT + "*");
        List<SysDict> sysDictList = sysDictService.getSysDictList();
        List<SysDictDetail> sysDictDetailList = sysDictDetailService.getSysDictDetailList();
        for (SysDict sysDict : sysDictList) {
            List<SysDictDetail> collect = sysDictDetailList.stream().filter(dictDetail -> sysDict.getCode().equals(dictDetail.getDictCode())).collect(Collectors.toList());
            redisUtil.setValue(RedisConstant.DICT + sysDict.getCode(), collect, null, null);
        }
    }
}
