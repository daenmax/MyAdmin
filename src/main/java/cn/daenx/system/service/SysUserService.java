package cn.daenx.system.service;

import cn.daenx.framework.common.vo.ComStatusUpdVo;
import cn.daenx.framework.common.vo.Result;
import cn.daenx.system.domain.dto.SysUserPageDto;
import cn.daenx.system.domain.po.SysUser;
import cn.daenx.system.domain.vo.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface SysUserService extends IService<SysUser> {
    /**
     * 通过手机号检查用户是否存在
     *
     * @param phone
     * @param nowId 排除ID
     * @return
     */
    Boolean checkUserByPhone(String phone, String nowId);

    /**
     * 通过邮箱检查用户是否存在
     *
     * @param email
     * @param nowId 排除ID
     * @return
     */
    Boolean checkUserByEmail(String email, String nowId);

    /**
     * 通过openId检查用户是否存在
     *
     * @param openId
     * @param nowId  排除ID
     * @return
     */
    Boolean checkUserByOpenId(String openId, String nowId);

    /**
     * 通过apiKey检查用户是否存在
     *
     * @param apiKey
     * @return
     */
    Boolean checkUserByApiKey(String apiKey);

    /**
     * 通过账号获取用户
     *
     * @param username
     * @return
     */
    SysUser getUserByUsername(String username);

    /**
     * 通过邮箱获取用户
     *
     * @param email
     * @return
     */
    SysUser getUserByEmail(String email);

    /**
     * 通过手机获取用户
     *
     * @param phone
     * @return
     */
    SysUser getUserByPhone(String phone);

    /**
     * 通过ID获取用户
     *
     * @param userId
     * @return
     */
    SysUser getUserByUserId(String userId);

    /**
     * 通过ID获取用户信息
     *
     * @param userId
     * @return
     */
    SysUserPageDto getUserInfoByUserId(String userId);

    /**
     * 校验用户状态是否正常
     *
     * @param sysUser
     * @return
     */
    Boolean validatedUser(SysUser sysUser);

    /**
     * 注册用户
     *
     * @param sysUser
     * @param deptCodes     部门编码
     * @param roleCodes     角色编码
     * @param positionCodes 岗位编码
     * @return
     */
    Boolean registerUser(SysUser sysUser, String[] deptCodes, String[] roleCodes, String[] positionCodes);

    /**
     * 个人信息
     *
     * @return
     */
    Map<String, Object> profile();

    /**
     * 修改个人资料
     *
     * @param vo
     */
    void updInfo(SysUserUpdInfoVo vo);

    /**
     * 修改个人密码
     *
     * @param vo
     */
    void updatePwd(SysUserUpdPwdVo vo);

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    IPage<SysUserPageDto> getPage(SysUserPageVo vo);

    /**
     * 获取所有列表，用于导出
     *
     * @param vo
     * @return
     */
    List<SysUserPageDto> getAll(SysUserPageVo vo);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    Map<String, Object> getInfo(String id);

    /**
     * 修改
     *
     * @param vo
     */
    void editInfo(SysUserUpdVo vo);

    /**
     * 新增
     *
     * @param vo
     */
    void addInfo(SysUserAddVo vo);

    /**
     * 修改状态
     *
     * @param vo
     */
    void changeStatus(ComStatusUpdVo vo);

    /**
     * 重置用户密码
     *
     * @param vo
     */
    void resetPwd(SysUserResetPwdVo vo);

    /**
     * 删除
     *
     * @param ids
     */
    void deleteByIds(List<String> ids);

    /**
     * 根据用户编号获取授权角色
     *
     * @param id
     * @return
     */
    Map<String, Object> authRole(String id);

    /**
     * 保存用户授权角色
     *
     * @param vo
     */
    void saveAuthRole(SysUserUpdAuthRoleVo vo);

    /**
     * 查询已分配该角色的用户列表
     *
     * @param vo
     * @param roleId
     * @return
     */
    IPage<SysUserPageDto> getUserListByRoleId(SysUserPageVo vo, String roleId);

    /**
     * 查询未分配该角色的用户列表
     *
     * @param vo
     * @param roleId
     * @return
     */
    IPage<SysUserPageDto> getUserListByUnRoleId(SysUserPageVo vo, String roleId);

    /**
     * 查询已分配该岗位的用户列表
     *
     * @param vo
     * @param positionId
     * @return
     */
    IPage<SysUserPageDto> getUserListByPositionId(SysUserPageVo vo, String positionId);

    /**
     * 查询未分配该岗位的用户列表
     *
     * @param vo
     * @param positionId
     * @return
     */
    IPage<SysUserPageDto> getUserListByUnPositionId(SysUserPageVo vo, String positionId);

    /**
     * 获取用户列表
     *
     * @param id       如果传ID，则会忽略其他全部参数
     * @param username
     * @param nickName
     * @param realName
     * @param phone
     * @param email
     * @return
     */
    List<SysUserPageDto> getUserList(String id, String username, String nickName, String realName, String phone, String email);

    /**
     * 修改头像
     * 返回头像链接
     *
     * @param file
     * @return
     */
    String avatar(MultipartFile file);

    /**
     * 获取邮箱验证码
     *
     * @param vo
     * @return
     */
    Result getEmailValidCode(SysUserUpdBindVo vo);

    /**
     * 获取手机验证码
     *
     * @param vo
     * @return
     */
    Result getPhoneValidCode(SysUserUpdBindVo vo);

    /**
     * 修改邮箱绑定
     *
     * @param vo
     * @return
     */
    Result updateBindEmail(SysUserUpdBindVo vo);

    /**
     * 修改手机绑定
     *
     * @param vo
     * @return
     */
    Result updateBindPhone(SysUserUpdBindVo vo);
}
