package cn.daenx.system.service.impl;

import cn.daenx.system.mapper.SysUserDetailMapper;
import cn.daenx.system.service.SysUserDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.daenx.system.domain.po.SysUserDetail;

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
