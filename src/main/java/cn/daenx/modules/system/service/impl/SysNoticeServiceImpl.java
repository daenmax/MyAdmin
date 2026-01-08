package cn.daenx.modules.system.service.impl;

import cn.daenx.modules.system.domain.vo.sysNotice.SysNoticePageVo;
import cn.daenx.framework.common.constant.SystemConstant;
import cn.daenx.framework.dataScope.annotation.DataScope;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.modules.system.domain.dto.sysNotice.SysNoticeAddDto;
import cn.daenx.modules.system.domain.dto.sysNotice.SysNoticePageDto;
import cn.daenx.modules.system.domain.dto.sysNotice.SysNoticeUpdDto;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.modules.system.domain.po.SysNotice;
import cn.daenx.modules.system.mapper.SysNoticeMapper;
import cn.daenx.modules.system.service.SysNoticeService;

import java.util.List;

@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements SysNoticeService {
    @Resource
    private SysNoticeMapper sysNoticeMapper;

    private QueryWrapper<SysNotice> getWrapper(SysNoticePageDto vo) {
        QueryWrapper<SysNotice> wrapper = new QueryWrapper<>();
        wrapper.like(ObjectUtil.isNotEmpty(vo.getTitle()), "sn.title", vo.getTitle());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getType()), "sn.type", vo.getType());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getStatus()), "sn.status", vo.getStatus());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getRemark()), "sn.remark", vo.getRemark());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getCreateName()), "su.username", vo.getCreateName());
        String startTime = vo.getStartTime();
        String endTime = vo.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), "sn.create_time", startTime, endTime);
        wrapper.eq("sn.is_delete", SystemConstant.IS_DELETE_NO);
        return wrapper;
    }

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @DataScope(alias = "sys_notice")
    @Override
    public IPage<SysNoticePageVo> getPage(SysNoticePageDto vo) {
        QueryWrapper<SysNotice> wrapper = getWrapper(vo);
        IPage<SysNoticePageVo> iPage = sysNoticeMapper.getPageWrapper(vo.getPage(true), wrapper);
        return iPage;
    }

    /**
     * 新增
     *
     * @param vo
     */
    @Override
    public void addInfo(SysNoticeAddDto vo) {
        SysNotice sysNotice = new SysNotice();
        sysNotice.setTitle(vo.getTitle());
        sysNotice.setContent(vo.getContent());
        sysNotice.setType(vo.getType());
        sysNotice.setStatus(vo.getStatus());
        sysNotice.setRemark(vo.getRemark());
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
     * @param vo
     */
    @DataScope(alias = "sys_notice")
    @Override
    public void editInfo(SysNoticeUpdDto vo) {
        LambdaUpdateWrapper<SysNotice> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysNotice::getId, vo.getId());
        updateWrapper.set(SysNotice::getTitle, vo.getTitle());
        updateWrapper.set(SysNotice::getContent, vo.getContent());
        updateWrapper.set(SysNotice::getType, vo.getType());
        updateWrapper.set(SysNotice::getStatus, vo.getStatus());
        updateWrapper.set(SysNotice::getRemark, vo.getRemark());
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
