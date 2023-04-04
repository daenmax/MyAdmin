package cn.daenx.myadmin.common.service;

import cn.daenx.myadmin.common.utils.LoginUtil;
import cn.daenx.myadmin.system.vo.SysLoginUserVo;
import cn.dev33.satoken.stp.StpInterface;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义权限验证接口扩展
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        SysLoginUserVo loginUser = LoginUtil.getLoginUser();
        return new ArrayList<>(loginUser.getMenuPermission());
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        SysLoginUserVo loginUser = LoginUtil.getLoginUser();
        return new ArrayList<>(loginUser.getRolePermission());
    }

}
