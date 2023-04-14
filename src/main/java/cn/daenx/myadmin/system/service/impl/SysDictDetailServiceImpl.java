package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.constant.RedisConstant;
import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.utils.RedisUtil;
import cn.daenx.myadmin.system.service.SysDictService;
import cn.daenx.myadmin.system.vo.SysDictDetailAddVo;
import cn.daenx.myadmin.system.vo.SysDictDetailPageVo;
import cn.daenx.myadmin.system.vo.SysDictDetailUpdVo;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.mapper.SysDictDetailMapper;
import cn.daenx.myadmin.system.po.SysDictDetail;
import cn.daenx.myadmin.system.service.SysDictDetailService;

@Service
public class SysDictDetailServiceImpl extends ServiceImpl<SysDictDetailMapper, SysDictDetail> implements SysDictDetailService {
    @Resource
    private RedisUtil redisUtil;
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
        Object object = redisUtil.getValue(RedisConstant.DICT + dictCode);
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
     * @param vo
     * @return
     */
    @Override
    public IPage<SysDictDetail> getPage(SysDictDetailPageVo vo) {
        LambdaQueryWrapper<SysDictDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(SysDictDetail::getSort);
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getDictCode()), SysDictDetail::getDictCode, vo.getDictCode());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getLabel()), SysDictDetail::getLabel, vo.getLabel());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getValue()), SysDictDetail::getValue, vo.getValue());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getStatus()), SysDictDetail::getStatus, vo.getStatus());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getRemark()), SysDictDetail::getRemark, vo.getRemark());
        String startTime = vo.getStartTime();
        String endTime = vo.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), SysDictDetail::getCreateTime, startTime, endTime);
        Page<SysDictDetail> sysDictDetailPage = sysDictDetailMapper.selectPage(vo.getPage(false), wrapper);
        return sysDictDetailPage;
    }

    /**
     * 获取所有列表，用于导出
     *
     * @param vo
     * @return
     */
    @Override
    public List<SysDictDetail> getAll(SysDictDetailPageVo vo) {
        LambdaQueryWrapper<SysDictDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(SysDictDetail::getSort);
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getDictCode()), SysDictDetail::getDictCode, vo.getDictCode());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getLabel()), SysDictDetail::getLabel, vo.getLabel());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getValue()), SysDictDetail::getValue, vo.getValue());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getStatus()), SysDictDetail::getStatus, vo.getStatus());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getRemark()), SysDictDetail::getRemark, vo.getRemark());
        String startTime = vo.getStartTime();
        String endTime = vo.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), SysDictDetail::getCreateTime, startTime, endTime);
        List<SysDictDetail> sysDictDetailList = sysDictDetailMapper.selectList(wrapper);
        return sysDictDetailList;
    }

    /**
     * 新增
     *
     * @param vo
     */
    @Override
    public void addInfo(SysDictDetailAddVo vo) {
        if (!sysDictService.checkDictExist(vo.getDictCode(), null)) {
            throw new MyException("字典编码不存在");
        }
        LambdaQueryWrapper<SysDictDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictDetail::getDictCode, vo.getDictCode());
        wrapper.eq(SysDictDetail::getValue, vo.getValue());
        Long aLong = sysDictDetailMapper.selectCount(wrapper);
        if (aLong > 0) {
            throw new MyException("字典键值已存在");
        }
        SysDictDetail sysDictDetail = new SysDictDetail();
        sysDictDetail.setDictCode(vo.getDictCode());
        sysDictDetail.setLabel(vo.getLabel());
        sysDictDetail.setValue(vo.getValue());
        sysDictDetail.setSort(vo.getSort());
        sysDictDetail.setCssClass(vo.getCssClass());
        sysDictDetail.setListClass(vo.getListClass());
        sysDictDetail.setStatus(vo.getStatus());
        sysDictDetail.setRemark(vo.getRemark());
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
     * @param vo
     */
    @Override
    public void editInfo(SysDictDetailUpdVo vo) {
        if (!sysDictService.checkDictExist(vo.getDictCode(), null)) {
            throw new MyException("字典编码不存在");
        }
        LambdaQueryWrapper<SysDictDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictDetail::getDictCode, vo.getDictCode());
        wrapper.eq(SysDictDetail::getValue, vo.getValue());
        wrapper.ne(SysDictDetail::getId, vo.getId());
        Long aLong = sysDictDetailMapper.selectCount(wrapper);
        if (aLong > 0) {
            throw new MyException("字典键值已存在");
        }
        LambdaUpdateWrapper<SysDictDetail> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysDictDetail::getId, vo.getId());
        updateWrapper.set(SysDictDetail::getDictCode, vo.getDictCode());
        updateWrapper.set(SysDictDetail::getLabel, vo.getLabel());
        updateWrapper.set(SysDictDetail::getValue, vo.getValue());
        updateWrapper.set(SysDictDetail::getSort, vo.getSort());
        updateWrapper.set(SysDictDetail::getCssClass, vo.getCssClass());
        updateWrapper.set(SysDictDetail::getListClass, vo.getListClass());
        updateWrapper.set(SysDictDetail::getStatus, vo.getStatus());
        updateWrapper.set(SysDictDetail::getRemark, vo.getRemark());
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
        sysDictDetailMapper.deleteBatchIds(ids);
        //刷新redis缓存
        sysDictService.refreshCache();
    }
}
