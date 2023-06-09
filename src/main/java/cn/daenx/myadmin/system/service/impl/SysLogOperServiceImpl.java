package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.annotation.DataScope;
import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.utils.MyUtil;
import cn.daenx.myadmin.system.domain.vo.SysLogOperPageVo;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.mapper.SysLogOperMapper;
import cn.daenx.myadmin.system.domain.po.SysLogOper;
import cn.daenx.myadmin.system.service.SysLogOperService;

import java.util.List;

@Service
public class SysLogOperServiceImpl extends ServiceImpl<SysLogOperMapper, SysLogOper> implements SysLogOperService {
    @Resource
    private SysLogOperMapper sysLogOperMapper;

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

    private LambdaQueryWrapper<SysLogOper> getWrapper(SysLogOperPageVo vo) {
        LambdaQueryWrapper<SysLogOper> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(ObjectUtil.isNotEmpty(vo.getName()), SysLogOper::getName, vo.getName());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getType()), SysLogOper::getType, vo.getType());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getMethod()), SysLogOper::getMethod, vo.getMethod());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getRequestType()), SysLogOper::getRequestType, vo.getRequestType());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getRequestUrl()), SysLogOper::getRequestUrl, vo.getRequestUrl());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getRequestIp()), SysLogOper::getRequestIp, vo.getRequestIp());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getRequestLocation()), SysLogOper::getRequestLocation, vo.getRequestLocation());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getRequestParams()), SysLogOper::getRequestParams, vo.getRequestParams());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getResponseResult()), SysLogOper::getResponseResult, vo.getResponseResult());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getErrorMsg()), SysLogOper::getErrorMsg, vo.getErrorMsg());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getStatus()), SysLogOper::getStatus, vo.getStatus());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getRemark()), SysLogOper::getRemark, vo.getRemark());
        String startTime = vo.getStartTime();
        String endTime = vo.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), SysLogOper::getRequestTime, startTime, endTime);
        return wrapper;
    }

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @DataScope(alias = "sys_log_oper")
    @Override
    public IPage<SysLogOper> getPage(SysLogOperPageVo vo) {
        LambdaQueryWrapper<SysLogOper> wrapper = getWrapper(vo);
        Page<SysLogOper> sysLogOperPage = sysLogOperMapper.selectPage(vo.getPage(true), wrapper);
        return sysLogOperPage;
    }

    /**
     * 获取所有列表，用于导出
     *
     * @param vo
     * @return
     */
    @DataScope(alias = "sys_log_oper")
    @Override
    public List<SysLogOper> getAll(SysLogOperPageVo vo) {
        LambdaQueryWrapper<SysLogOper> wrapper = getWrapper(vo);
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
        int i = sysLogOperMapper.deleteBatchIds(ids);
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
