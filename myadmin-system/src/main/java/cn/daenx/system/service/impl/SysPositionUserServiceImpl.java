package cn.daenx.system.service.impl;

import cn.daenx.system.mapper.SysPositionUserMapper;
import cn.daenx.system.service.SysPositionUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.daenx.system.domain.po.SysPositionUser;
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

    /**
     * 删除用户的指定岗位
     *
     * @param userId
     * @param positionId
     */
    @Override
    public Boolean delUserPosition(String userId, String positionId) {
        LambdaQueryWrapper<SysPositionUser> wrapperDel = new LambdaQueryWrapper<>();
        wrapperDel.eq(SysPositionUser::getUserId, userId);
        wrapperDel.eq(SysPositionUser::getPositionId, positionId);
        return sysPositionUserMapper.delete(wrapperDel) > 0;
    }

    /**
     * 给用户添加指定岗位
     *
     * @param userId
     * @param positionId
     * @return
     */
    @Override
    public Boolean addUserPosition(String userId, String positionId) {
        SysPositionUser sysPositionUser = new SysPositionUser();
        sysPositionUser.setPositionId(positionId);
        sysPositionUser.setUserId(userId);
        return sysPositionUserMapper.insert(sysPositionUser) > 0;
    }
}
