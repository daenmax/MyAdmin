package cn.daenx.modules.system.service.impl;

import cn.daenx.framework.common.constant.SystemConstant;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.dataScope.annotation.DataScope;
import cn.daenx.modules.system.domain.dto.sysNotice.SysNoticeAddDto;
import cn.daenx.modules.system.domain.dto.sysNotice.SysNoticePageDto;
import cn.daenx.modules.system.domain.dto.sysNotice.SysNoticeUpdDto;
import cn.daenx.modules.system.domain.po.SysNotice;
import cn.daenx.modules.system.domain.vo.sysNotice.SysNoticePageVo;
import cn.daenx.modules.system.mapper.SysNoticeMapper;
import cn.daenx.modules.system.service.SysNoticeService;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements SysNoticeService {
    @Resource
    private SysNoticeMapper sysNoticeMapper;

    private QueryWrapper<SysNotice> getWrapper(SysNoticePageDto dto) {
        QueryWrapper<SysNotice> wrapper = new QueryWrapper<>();
        wrapper.like(ObjectUtil.isNotEmpty(dto.getTitle()), "sn.title", dto.getTitle());
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getType()), "sn.type", dto.getType());
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getStatus()), "sn.status", dto.getStatus());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getRemark()), "sn.remark", dto.getRemark());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getCreateName()), "su.username", dto.getCreateName());
        String startTime = dto.getStartTime();
        String endTime = dto.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), "sn.create_time", startTime, endTime);
        wrapper.eq("sn.is_delete", SystemConstant.IS_DELETE_NO);
        return wrapper;
    }

    /**
     * 分页列表
     *
     * @param dto
     * @return
     */
    @DataScope(alias = "sys_notice")
    @Override
    public IPage<SysNoticePageVo> getPage(SysNoticePageDto dto) {
        QueryWrapper<SysNotice> wrapper = getWrapper(dto);
        IPage<SysNoticePageVo> iPage = sysNoticeMapper.getPageWrapper(dto.getPage(true), wrapper);
        return iPage;
    }

    /**
     * 新增
     *
     * @param dto
     */
    @Override
    public void addInfo(SysNoticeAddDto dto) {
        SysNotice sysNotice = new SysNotice();
        sysNotice.setTitle(dto.getTitle());
        sysNotice.setContent(dto.getContent());
        sysNotice.setType(dto.getType());
        sysNotice.setStatus(dto.getStatus());
        sysNotice.setRemark(dto.getRemark());
        int insert = sysNoticeMapper.insert(sysNotice);
        if (insert < 1) {
            throw new MyException("新增失败");
        }
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @DataScope(alias = "sys_notice")
    @Override
    public SysNotice getInfo(String id) {
        return sysNoticeMapper.selectById(id);
    }

    /**
     * 修改
     *
     * @param dto
     */
    @DataScope(alias = "sys_notice")
    @Override
    public void editInfo(SysNoticeUpdDto dto) {
        LambdaUpdateWrapper<SysNotice> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysNotice::getId, dto.getId());
        updateWrapper.set(SysNotice::getTitle, dto.getTitle());
        updateWrapper.set(SysNotice::getContent, dto.getContent());
        updateWrapper.set(SysNotice::getType, dto.getType());
        updateWrapper.set(SysNotice::getStatus, dto.getStatus());
        updateWrapper.set(SysNotice::getRemark, dto.getRemark());
        int rows = sysNoticeMapper.update(new SysNotice(), updateWrapper);
        if (rows < 1) {
            throw new MyException("修改失败");
        }
    }

    /**
     * 删除
     *
     * @param ids
     */
    @DataScope(alias = "sys_notice")
    @Override
    public void deleteByIds(List<String> ids) {
        int i = sysNoticeMapper.deleteByIds(ids);
        if (i < 1) {
            throw new MyException("删除失败");
        }
    }
}
