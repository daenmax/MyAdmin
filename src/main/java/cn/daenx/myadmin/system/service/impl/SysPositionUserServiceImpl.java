package cn.daenx.myadmin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.mapper.SysPositionUserMapper;
import cn.daenx.myadmin.system.po.SysPositionUser;
import cn.daenx.myadmin.system.service.SysPositionUserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysPositionUserServiceImpl extends ServiceImpl<SysPositionUserMapper, SysPositionUser> implements SysPositionUserService {
    @Resource
    private SysPositionUserMapper sysPositionUserMapper;

    /**
     * 更新用户岗位关联信息
     *
     * @param userId
     * @param positionIds
     */
    @Override
    public void handleUserPosition(String userId, List<String> positionIds) {
        LambdaQueryWrapper<SysPositionUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysPositionUser::getUserId, userId);
        sysPositionUserMapper.delete(wrapper);
        if (positionIds != null) {
            List<SysPositionUser> list = new ArrayList<>();
            for (String positionId : positionIds) {
                SysPositionUser sysPositionUser = new SysPositionUser();
                sysPositionUser.setPositionId(positionId);
                sysPositionUser.setUserId(userId);
                list.add(sysPositionUser);
            }
            saveBatch(list);
        }
    }

    /**
     * 删除用户岗位关联信息
     *
     * @param userIds
     */
    @Override
    public void delUserPosition(List<String> userIds) {
        LambdaQueryWrapper<SysPositionUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SysPositionUser::getUserId, userIds);
        sysPositionUserMapper.delete(wrapper);
    }
}
