package cn.daenx.modules.system.service.impl;

import cn.daenx.framework.common.domain.vo.system.other.SysLogOperVo;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.utils.MyUtil;
import cn.daenx.framework.dataScope.annotation.DataScope;
import cn.daenx.modules.system.domain.dto.sysLog.SysLogOperPageDto;
import cn.daenx.modules.system.domain.po.SysLogOper;
import cn.daenx.modules.system.mapper.SysLogOperMapper;
import cn.daenx.modules.system.service.SysLogOperService;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysLogOperServiceImpl extends ServiceImpl<SysLogOperMapper, SysLogOper> implements SysLogOperService {
    @Resource
    private SysLogOperMapper sysLogOperMapper;

    @Async("CommonTaskExecutor")
    @EventListener
    public void saveLog(SysLogOperVo sysLogOperVo){
        SysLogOper sysLogOper = new SysLogOper();
        BeanUtils.copyProperties(sysLogOperVo, sysLogOper);
        sysLogOperMapper.insert(sysLogOper);
    }

    /**
     * 记录操作日志
     *
     * @param sysLogOper
     */
    @Override
    public void saveOper(SysLogOper sysLogOper) {
        if (ObjectUtil.isNotEmpty(sysLogOper.getRequestIp())) {
            sysLogOper.setRequestLocation(MyUtil.getIpLocation(sysLogOper.getRequestIp()));
        }
        sysLogOperMapper.insert(sysLogOper);
    }

    private LambdaQueryWrapper<SysLogOper> getWrapper(SysLogOperPageDto dto) {
        LambdaQueryWrapper<SysLogOper> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(ObjectUtil.isNotEmpty(dto.getName()), SysLogOper::getName, dto.getName());
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getType()), SysLogOper::getType, dto.getType());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getMethod()), SysLogOper::getMethod, dto.getMethod());
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getRequestType()), SysLogOper::getRequestType, dto.getRequestType());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getRequestUrl()), SysLogOper::getRequestUrl, dto.getRequestUrl());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getRequestIp()), SysLogOper::getRequestIp, dto.getRequestIp());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getRequestLocation()), SysLogOper::getRequestLocation, dto.getRequestLocation());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getRequestParams()), SysLogOper::getRequestParams, dto.getRequestParams());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getResponseResult()), SysLogOper::getResponseResult, dto.getResponseResult());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getErrorMsg()), SysLogOper::getErrorMsg, dto.getErrorMsg());
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getStatus()), SysLogOper::getStatus, dto.getStatus());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getRemark()), SysLogOper::getRemark, dto.getRemark());
        String startTime = dto.getStartTime();
        String endTime = dto.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), SysLogOper::getRequestTime, startTime, endTime);
        return wrapper;
    }

    /**
     * 分页列表
     *
     * @param dto
     * @return
     */
    @DataScope(alias = "sys_log_oper")
    @Override
    public IPage<SysLogOper> getPage(SysLogOperPageDto dto) {
        LambdaQueryWrapper<SysLogOper> wrapper = getWrapper(dto);
        Page<SysLogOper> sysLogOperPage = sysLogOperMapper.selectPage(dto.getPage(true), wrapper);
        return sysLogOperPage;
    }

    /**
     * 获取所有列表，用于导出
     *
     * @param dto
     * @return
     */
    @DataScope(alias = "sys_log_oper")
    @Override
    public List<SysLogOper> getAll(SysLogOperPageDto dto) {
        LambdaQueryWrapper<SysLogOper> wrapper = getWrapper(dto);
        List<SysLogOper> sysLogOperList = sysLogOperMapper.selectList(wrapper);
        return sysLogOperList;
    }

    /**
     * 删除
     *
     * @param ids
     */
    @DataScope(alias = "sys_log_oper")
    @Override
    public void deleteByIds(List<String> ids) {
        int i = sysLogOperMapper.deleteByIds(ids);
        if (i < 1) {
            throw new MyException("删除失败");
        }
    }

    /**
     * 清空
     */
    @DataScope(alias = "sys_log_oper")
    @Override
    public void clean() {
        LambdaQueryWrapper<SysLogOper> wrapper = new LambdaQueryWrapper<>();
        sysLogOperMapper.delete(wrapper);
    }
}
