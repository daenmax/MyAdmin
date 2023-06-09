package cn.daenx.myadmin.system.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.mapper.SysUserDetailMapper;
import cn.daenx.myadmin.system.domain.po.SysUserDetail;
import cn.daenx.myadmin.system.service.SysUserDetailService;

@Service
public class SysUserDetailServiceImpl extends ServiceImpl<SysUserDetailMapper, SysUserDetail> implements SysUserDetailService {
    @Resource
    private SysUserDetailMapper sysUserDetailMapper;

    /**
     * 创建用户详细信息
     *
     * @param sysUserDetail
     * @return
     */
    @Override
    public Boolean createDetail(SysUserDetail sysUserDetail) {
        return sysUserDetailMapper.insert(sysUserDetail) > 0;
    }
}
