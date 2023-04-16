package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.constant.RedisConstant;
import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.oss.vo.OssProperties;
import cn.daenx.myadmin.common.utils.RedisUtil;
import cn.daenx.myadmin.system.po.SysOssConfig;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.po.SysFile;
import cn.daenx.myadmin.system.mapper.SysFileMapper;
import cn.daenx.myadmin.system.service.SysFileService;

@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService {
    @Resource
    private SysFileMapper sysFileMapper;

    /**
     * 保存文件
     *
     * @param sysFile
     */
    @Override
    public SysFile saveFile(SysFile sysFile) {
        Object object = RedisUtil.getValue(RedisConstant.OSS);
        if (ObjectUtil.isEmpty(object)) {
            throw new MyException("未配置OSS配置信息，请联系管理员");
        }
        SysOssConfig sysOssConfig = JSON.parseObject(JSON.toJSONString(object), SysOssConfig.class);
//        sysFile.setOssId();
        return null;
    }
}
