package cn.daenx.modules.system.service.impl;

import cn.daenx.framework.common.constant.SystemConstant;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.utils.MyUtil;
import cn.daenx.framework.dataScope.annotation.DataScope;
import cn.daenx.modules.system.domain.dto.sysLog.SysLogLoginPageDto;
import cn.daenx.modules.system.domain.po.SysLogLogin;
import cn.daenx.modules.system.mapper.SysLogLoginMapper;
import cn.daenx.modules.system.service.SysLogLoginService;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.useragent.UserAgent;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
    @Async("CommonTaskExecutor")
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

    private LambdaQueryWrapper<SysLogLogin> getWrapper(SysLogLoginPageDto dto) {
        LambdaQueryWrapper<SysLogLogin> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(ObjectUtil.isNotEmpty(dto.getUsername()), SysLogLogin::getUsername, dto.getUsername());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getIp()), SysLogLogin::getIp, dto.getIp());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getLocation()), SysLogLogin::getLocation, dto.getLocation());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getBrowser()), SysLogLogin::getBrowser, dto.getBrowser());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getOs()), SysLogLogin::getOs, dto.getOs());
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getStatus()), SysLogLogin::getStatus, dto.getStatus());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getRemark()), SysLogLogin::getRemark, dto.getRemark());
        String startTime = dto.getStartTime();
        String endTime = dto.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), SysLogLogin::getCreateTime, startTime, endTime);
        return wrapper;
    }

    /**
     * 分页列表
     *
     * @param dto
     * @return
     */
    @DataScope(alias = "sys_log_login")
    @Override
    public IPage<SysLogLogin> getPage(SysLogLoginPageDto dto) {
        LambdaQueryWrapper<SysLogLogin> wrapper = getWrapper(dto);
        Page<SysLogLogin> sysLogLoginPage = sysLogLoginMapper.selectPage(dto.getPage(true), wrapper);
        return sysLogLoginPage;
    }

    /**
     * 获取所有列表，用于导出
     *
     * @param dto
     * @return
     */
    @DataScope(alias = "sys_log_login")
    @Override
    public List<SysLogLogin> getAll(SysLogLoginPageDto dto) {
        LambdaQueryWrapper<SysLogLogin> wrapper = getWrapper(dto);
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
        int i = sysLogLoginMapper.deleteByIds(ids);
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
