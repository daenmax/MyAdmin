package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.daenx.myadmin.system.domain.dto.SysJobLogPageDto;
import cn.daenx.myadmin.system.mapper.SysJobLogMapper;
import cn.daenx.myadmin.system.domain.po.SysJobLog;
import cn.daenx.myadmin.system.service.SysJobLogService;
import cn.daenx.myadmin.system.domain.vo.SysJobLogPageVo;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysJobLogServiceImpl extends ServiceImpl<SysJobLogMapper, SysJobLog> implements SysJobLogService {
    @Resource
    private SysJobLogMapper sysJobLogMapper;

    private QueryWrapper<SysJobLog> getWrapperQuery(SysJobLogPageVo vo) {
        QueryWrapper<SysJobLog> wrapper = new QueryWrapper<>();
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getId()), "sjl.id", vo.getId());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getJobName()), "sj.job_name", vo.getJobName());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getStatus()), "sjl.status", vo.getStatus());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getRemark()), "sjl.remark", vo.getRemark());
        String startTime = vo.getStartTime();
        String endTime = vo.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), "sjl.create_time", startTime, endTime);
        wrapper.eq("sjl.is_delete", SystemConstant.IS_DELETE_NO);
        return wrapper;
    }

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @Override
    public IPage<SysJobLogPageDto> getPage(SysJobLogPageVo vo) {
        QueryWrapper<SysJobLog> wrapperQuery = getWrapperQuery(vo);
        IPage<SysJobLogPageDto> iPage = sysJobLogMapper.getPageWrapper(vo.getPage(true), wrapperQuery);
        return iPage;
    }

    /**
     * 获取所有列表，用于导出
     *
     * @param vo
     * @return
     */
    @Override
    public List<SysJobLogPageDto> getAll(SysJobLogPageVo vo) {
        QueryWrapper<SysJobLog> wrapperQuery = getWrapperQuery(vo);
        List<SysJobLogPageDto> list = sysJobLogMapper.getAll(wrapperQuery);
        return list;
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @Override
    public SysJobLogPageDto getInfo(String id) {
        SysJobLogPageVo vo = new SysJobLogPageVo();
        vo.setId(id);
        QueryWrapper<SysJobLog> wrapperQuery = getWrapperQuery(vo);
        List<SysJobLogPageDto> list = sysJobLogMapper.getAll(wrapperQuery);
        if (list.size() == 0) {
            throw new MyException("数据不存在");
        }
        return list.get(0);
    }

    /**
     * 删除
     * 未加数据权限
     *
     * @param ids
     */

    @Override
    public void deleteByIds(List<String> ids) {
        int i = sysJobLogMapper.deleteBatchIds(ids);
        if (i < 1) {
            throw new MyException("删除失败");
        }
    }

    /**
     * 清空日志
     * 未加数据权限
     */
    @Override
    public void clean() {
        LambdaQueryWrapper<SysJobLog> wrapper = new LambdaQueryWrapper<>();
        sysJobLogMapper.delete(wrapper);
    }
}
