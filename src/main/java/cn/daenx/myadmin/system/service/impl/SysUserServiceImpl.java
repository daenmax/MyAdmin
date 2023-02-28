package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.constant.Constant;
import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.po.SysUser;
import cn.daenx.myadmin.system.mapper.SysUserMapper;
import cn.daenx.myadmin.system.service.SysUserService;

import java.time.LocalDateTime;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * 通过账号获取用户
     *
     * @param username
     * @return
     */
    @Override
    public SysUser getUserByUsername(String username) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, username);
        return sysUserMapper.selectOne(wrapper);
    }

    /**
     * 校验用户状态是否正常
     *
     * @param sysUser
     * @return
     */
    @Override
    public Boolean validatedUser(SysUser sysUser) {
        if (SystemConstant.USER_STATUS_DISABLE.equals(sysUser.getStatus())) {
            throw new MyException("账号已被停用");
        }
        if (SystemConstant.USER_STATUS_OFF.equals(sysUser.getStatus())) {
            throw new MyException("账号已注销");
        }
        //判断是否锁定中
        if (ObjectUtil.isNotEmpty(sysUser.getBanToTime())) {
            if (LocalDateTime.now().isBefore(sysUser.getBanToTime())) {
                String banToTime = LocalDateTimeUtil.format(sysUser.getBanToTime(), Constant.DATE_TIME_FORMAT);
                throw new MyException("账号被锁定，自动解除时间：" + banToTime);
            }
        }
        if (SystemConstant.USER_STATUS_NORMAL.equals(sysUser.getStatus())) {
            //正常，需要判断是否到期
            if (ObjectUtil.isEmpty(sysUser.getExpireToTime())) {
                return true;
            } else {
                if (LocalDateTime.now().isAfter(sysUser.getExpireToTime())) {
                    throw new MyException("账号已到期");
                }
            }
        }
        return true;
    }

    /**
     * 更新用户登录信息
     *
     * @param userId
     * @param ip
     */
    @Override
    public void updateUserLogin(String userId, String ip) {
        LambdaUpdateWrapper<SysUser> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysUser::getId, userId);
        wrapper.set(SysUser::getLoginIp, ip);
        wrapper.set(SysUser::getLoginTime, LocalDateTime.now());
        sysUserMapper.update(null, wrapper);
    }
}
