package cn.daenx.modules.system.service.impl;

import cn.daenx.framework.cache.utils.CacheUtil;
import cn.daenx.framework.common.constant.RedisConstant;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.modules.system.domain.dto.sysDict.SysDictDetailAddDto;
import cn.daenx.modules.system.domain.dto.sysDict.SysDictDetailPageDto;
import cn.daenx.modules.system.domain.dto.sysDict.SysDictDetailUpdDto;
import cn.daenx.modules.system.domain.po.SysDictDetail;
import cn.daenx.modules.system.mapper.SysDictDetailMapper;
import cn.daenx.modules.system.service.SysDictDetailService;
import cn.daenx.modules.system.service.SysDictService;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysDictDetailServiceImpl extends ServiceImpl<SysDictDetailMapper, SysDictDetail> implements SysDictDetailService {
    @Resource
    private SysDictService sysDictService;
    @Resource
    private SysDictDetailMapper sysDictDetailMapper;


    /**
     * 根据字典编码查询字典详细信息
     *
     * @param dictCode
     * @return
     */
    @Override
    public List<SysDictDetail> getDictDetailByCodeFromRedis(String dictCode) {
        Object object = CacheUtil.getValue(RedisConstant.DICT + dictCode);
        List<SysDictDetail> list = JSON.parseArray(JSON.toJSONString(object), SysDictDetail.class);
        return list;
    }

    /**
     * 根据字典编码和键值查询字典详细信息
     *
     * @param dictCode
     * @param value
     * @return
     */
    @Override
    public SysDictDetail getDictDetailValueByCodeFromRedis(String dictCode, String value) {
        List<SysDictDetail> sysUserType = getDictDetailByCodeFromRedis(dictCode);
        List<SysDictDetail> collect = sysUserType.stream().filter(detail -> value.equals(detail.getValue())).collect(Collectors.toList());
        if (collect.size() > 0) {
            return collect.get(0);
        }
        return new SysDictDetail();
    }

    /**
     * 分页列表
     *
     * @param dto
     * @return
     */
    @Override
    public IPage<SysDictDetail> getPage(SysDictDetailPageDto dto) {
        LambdaQueryWrapper<SysDictDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(SysDictDetail::getSort);
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getDictCode()), SysDictDetail::getDictCode, dto.getDictCode());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getLabel()), SysDictDetail::getLabel, dto.getLabel());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getValue()), SysDictDetail::getValue, dto.getValue());
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getStatus()), SysDictDetail::getStatus, dto.getStatus());
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getRemark()), SysDictDetail::getRemark, dto.getRemark());
        String startTime = dto.getStartTime();
        String endTime = dto.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), SysDictDetail::getCreateTime, startTime, endTime);
        Page<SysDictDetail> sysDictDetailPage = sysDictDetailMapper.selectPage(dto.getPage(false), wrapper);
        return sysDictDetailPage;
    }

    /**
     * 获取所有列表，用于导出
     *
     * @param dto
     * @return
     */
    @Override
    public List<SysDictDetail> getAll(SysDictDetailPageDto dto) {
        LambdaQueryWrapper<SysDictDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(SysDictDetail::getSort);
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getDictCode()), SysDictDetail::getDictCode, dto.getDictCode());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getLabel()), SysDictDetail::getLabel, dto.getLabel());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getValue()), SysDictDetail::getValue, dto.getValue());
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getStatus()), SysDictDetail::getStatus, dto.getStatus());
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getRemark()), SysDictDetail::getRemark, dto.getRemark());
        String startTime = dto.getStartTime();
        String endTime = dto.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), SysDictDetail::getCreateTime, startTime, endTime);
        List<SysDictDetail> sysDictDetailList = sysDictDetailMapper.selectList(wrapper);
        return sysDictDetailList;
    }

    /**
     * 新增
     *
     * @param dto
     */
    @Override
    public void addInfo(SysDictDetailAddDto dto) {
        if (!sysDictService.checkDictExist(dto.getDictCode(), null)) {
            throw new MyException("字典编码不存在");
        }
        LambdaQueryWrapper<SysDictDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictDetail::getDictCode, dto.getDictCode());
        wrapper.eq(SysDictDetail::getValue, dto.getValue());
        Long aLong = sysDictDetailMapper.selectCount(wrapper);
        if (aLong > 0) {
            throw new MyException("字典键值已存在");
        }
        SysDictDetail sysDictDetail = new SysDictDetail();
        sysDictDetail.setDictCode(dto.getDictCode());
        sysDictDetail.setLabel(dto.getLabel());
        sysDictDetail.setValue(dto.getValue());
        sysDictDetail.setSort(dto.getSort());
        sysDictDetail.setCssClass(dto.getCssClass());
        sysDictDetail.setListClass(dto.getListClass());
        sysDictDetail.setStatus(dto.getStatus());
        sysDictDetail.setRemark(dto.getRemark());
        int insert = sysDictDetailMapper.insert(sysDictDetail);
        if (insert < 1) {
            throw new MyException("新增失败");
        }
        //刷新redis缓存
        sysDictService.refreshCache();
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @Override
    public SysDictDetail getInfo(String id) {
        return sysDictDetailMapper.selectById(id);
    }

    /**
     * 修改
     *
     * @param dto
     */
    @Override
    public void editInfo(SysDictDetailUpdDto dto) {
        if (!sysDictService.checkDictExist(dto.getDictCode(), null)) {
            throw new MyException("字典编码不存在");
        }
        LambdaQueryWrapper<SysDictDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictDetail::getDictCode, dto.getDictCode());
        wrapper.eq(SysDictDetail::getValue, dto.getValue());
        wrapper.ne(SysDictDetail::getId, dto.getId());
        Long aLong = sysDictDetailMapper.selectCount(wrapper);
        if (aLong > 0) {
            throw new MyException("字典键值已存在");
        }
        LambdaUpdateWrapper<SysDictDetail> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysDictDetail::getId, dto.getId());
        updateWrapper.set(SysDictDetail::getDictCode, dto.getDictCode());
        updateWrapper.set(SysDictDetail::getLabel, dto.getLabel());
        updateWrapper.set(SysDictDetail::getValue, dto.getValue());
        updateWrapper.set(SysDictDetail::getSort, dto.getSort());
        updateWrapper.set(SysDictDetail::getCssClass, dto.getCssClass());
        updateWrapper.set(SysDictDetail::getListClass, dto.getListClass());
        updateWrapper.set(SysDictDetail::getStatus, dto.getStatus());
        updateWrapper.set(SysDictDetail::getRemark, dto.getRemark());
        int update = sysDictDetailMapper.update(new SysDictDetail(), updateWrapper);
        if (update < 1) {
            throw new MyException("修改字典明细失败");
        }
        //刷新redis缓存
        sysDictService.refreshCache();
    }

    /**
     * 删除
     *
     * @param ids
     */
    @Override
    public void deleteByIds(List<String> ids) {
        //删除明细
        sysDictDetailMapper.deleteByIds(ids);
        //刷新redis缓存
        sysDictService.refreshCache();
    }
}
