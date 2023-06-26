package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.framework.dataScope.annotation.DataScope;
import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.utils.MyUtil;
import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.daenx.myadmin.system.domain.vo.SysLogLoginPageVo;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.useragent.UserAgent;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.mapper.SysLogLoginMapper;
import cn.daenx.myadmin.system.domain.po.SysLogLogin;
import cn.daenx.myadmin.system.service.SysLogLoginService;

import java.util.List;

@Service
public class SysLogLoginServiceImpl extends ServiceImpl<SysLogLoginMapper, SysLogLogin> implements SysLogLoginService {

    @Resource
    private SysLogLoginMapper sysLogLoginMapper;

    /**
     * 记录登录日志
     *
     * @param userId
     * @param userName
     * @param status
     * @param remark
     * @param ip
     * @param userAgent
     */
    @Override
    @Async
    public void saveLogin(String userId, String userName, String status, String remark, String ip, UserAgent userAgent) {
        SysLogLogin sysLogLogin = new SysLogLogin();
        sysLogLogin.setUsername(userName);
        if (ObjectUtil.isNotEmpty(ip)) {
            sysLogLogin.setIp(ip);
            sysLogLogin.setLocation(MyUtil.getIpLocation(sysLogLogin.getIp()));
        }
        sysLogLogin.setBrowser(userAgent.getBrowser().getName());
        sysLogLogin.setOs(userAgent.getOs().getName());
        sysLogLogin.setCreateId(userId);
        sysLogLogin.setUpdateId(userId);
        sysLogLogin.setStatus(SystemConstant.LOGIN_SUCCESS);
        sysLogLogin.setRemark(remark);
        sysLogLoginMapper.insert(sysLogLogin);
    }

    private LambdaQueryWrapper<SysLogLogin> getWrapper(SysLogLoginPageVo vo) {
        LambdaQueryWrapper<SysLogLogin> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(ObjectUtil.isNotEmpty(vo.getUsername()), SysLogLogin::getUsername, vo.getUsername());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getIp()), SysLogLogin::getIp, vo.getIp());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getLocation()), SysLogLogin::getLocation, vo.getLocation());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getBrowser()), SysLogLogin::getBrowser, vo.getBrowser());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getOs()), SysLogLogin::getOs, vo.getOs());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getStatus()), SysLogLogin::getStatus, vo.getStatus());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getRemark()), SysLogLogin::getRemark, vo.getRemark());
        String startTime = vo.getStartTime();
        String endTime = vo.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), SysLogLogin::getCreateTime, startTime, endTime);
        return wrapper;
    }

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @DataScope(alias = "sys_log_login")
    @Override
    public IPage<SysLogLogin> getPage(SysLogLoginPageVo vo) {
        LambdaQueryWrapper<SysLogLogin> wrapper = getWrapper(vo);
        Page<SysLogLogin> sysLogLoginPage = sysLogLoginMapper.selectPage(vo.getPage(true), wrapper);
        return sysLogLoginPage;
    }

    /**
     * 获取所有列表，用于导出
     *
     * @param vo
     * @return
     */
    @DataScope(alias = "sys_log_login")
    @Override
    public List<SysLogLogin> getAll(SysLogLoginPageVo vo) {
        LambdaQueryWrapper<SysLogLogin> wrapper = getWrapper(vo);
        List<SysLogLogin> sysLogLoginList = sysLogLoginMapper.selectList(wrapper);
        return sysLogLoginList;
    }

    /**
     * 删除
     *
     * @param ids
     */
    @DataScope(alias = "sys_log_login")
    @Override
    public void deleteByIds(List<String> ids) {
        int i = sysLogLoginMapper.deleteBatchIds(ids);
        if (i < 1) {
            throw new MyException("删除失败");
        }
    }

    /**
     * 清空
     */
    @DataScope(alias = "sys_log_login")
    @Override
    public void clean() {
        LambdaQueryWrapper<SysLogLogin> wrapper = new LambdaQueryWrapper<>();
        sysLogLoginMapper.delete(wrapper);
    }
}
