package cn.daenx.modules.system.service.impl;

import cn.daenx.framework.cache.utils.CacheUtil;
import cn.daenx.framework.common.constant.CommonConstant;
import cn.daenx.framework.common.constant.RedisConstant;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.utils.MyUtil;
import cn.daenx.modules.system.domain.dto.sysDict.SysDictAddDto;
import cn.daenx.modules.system.domain.dto.sysDict.SysDictPageDto;
import cn.daenx.modules.system.domain.dto.sysDict.SysDictUpdDto;
import cn.daenx.modules.system.domain.po.SysDict;
import cn.daenx.modules.system.domain.po.SysDictDetail;
import cn.daenx.modules.system.mapper.SysDictDetailMapper;
import cn.daenx.modules.system.mapper.SysDictMapper;
import cn.daenx.modules.system.service.SysDictService;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {
    @Resource
    private SysDictMapper sysDictMapper;
    @Resource
    private SysDictDetailMapper sysDictDetailMapper;

    @Override
    public List<SysDict> getSysDictList() {
        LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDict::getStatus, CommonConstant.STATUS_NORMAL);
        List<SysDict> sysDictList = sysDictMapper.selectList(wrapper);
        return sysDictList;
    }

    /**
     * 分页列表
     *
     * @param dto
     * @return
     */
    @Override
    public IPage<SysDict> getPage(SysDictPageDto dto) {
        LambdaQueryWrapper<SysDict> wrapper = getWrapper(dto);
        Page<SysDict> sysDictPage = sysDictMapper.selectPage(dto.getPage(true), wrapper);
        return sysDictPage;
    }

    /**
     * 获取所有列表，用于导出
     *
     * @param dto
     * @return
     */
    @Override
    public List<SysDict> getAll(SysDictPageDto dto) {
        LambdaQueryWrapper<SysDict> wrapper = getWrapper(dto);
        List<SysDict> sysDictList = sysDictMapper.selectList(wrapper);
        return sysDictList;
    }

    private LambdaQueryWrapper<SysDict> getWrapper(SysDictPageDto dto) {
        LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(ObjectUtil.isNotEmpty(dto.getName()), SysDict::getName, dto.getName());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getCode()), SysDict::getCode, dto.getCode());
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getStatus()), SysDict::getStatus, dto.getStatus());
        String startTime = dto.getStartTime();
        String endTime = dto.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), SysDict::getCreateTime, startTime, endTime);
        return wrapper;
    }

    /**
     * +
     * 新增
     *
     * @param dto
     */
    @Override
    public void addInfo(SysDictAddDto dto) {
        if (checkDictExist(dto.getCode(), null)) {
            throw new MyException("字典编码已存在");
        }
        SysDict sysDict = new SysDict();
        sysDict.setCode(dto.getCode());
        sysDict.setName(dto.getName());
        sysDict.setStatus(dto.getStatus());
        sysDict.setRemark(dto.getRemark());
        int insert = sysDictMapper.insert(sysDict);
        if (insert > 0) {
            //刷新redis缓存
            refreshCache();
            return;
        }
        throw new MyException("新增失败");
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @Override
    public SysDict getInfo(String id) {
        return sysDictMapper.selectById(id);
    }

    /**
     * 修改
     *
     * @param dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editInfo(SysDictUpdDto dto) {
        if (checkDictExist(dto.getCode(), dto.getId())) {
            throw new MyException("字典编码已存在");
        }
        SysDict info = getInfo(dto.getId());
        LambdaUpdateWrapper<SysDict> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysDict::getId, dto.getId());
        wrapper.set(SysDict::getName, dto.getName());
        wrapper.set(SysDict::getCode, dto.getCode());
        wrapper.set(SysDict::getStatus, dto.getStatus());
        wrapper.set(SysDict::getRemark, dto.getRemark());
        int rows = sysDictMapper.update(new SysDict(), wrapper);
        if (rows > 0) {
            LambdaQueryWrapper<SysDictDetail> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysDictDetail::getDictCode, info.getCode());
            Long count = sysDictDetailMapper.selectCount(queryWrapper);
            if (count > 0) {
                //修改字典明细
                LambdaUpdateWrapper<SysDictDetail> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.set(SysDictDetail::getDictCode, dto.getCode());
                updateWrapper.eq(SysDictDetail::getDictCode, info.getCode());
                int update = sysDictDetailMapper.update(new SysDictDetail(), updateWrapper);
                if (update < 1) {
                    throw new MyException("修改字典明细失败");
                }
            }
            //刷新redis缓存
            refreshCache();
        } else {
            throw new MyException("修改字典失败");
        }
    }

    /**
     * 删除
     *
     * @param ids
     */
    @Override
    public void deleteByIds(List<String> ids) {
        //删除明细
        LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SysDict::getId, ids);
        List<SysDict> sysDictList = sysDictMapper.selectList(wrapper);
        List<String> codeList = MyUtil.joinToList(sysDictList, SysDict::getCode);
        LambdaQueryWrapper<SysDictDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysDictDetail::getDictCode, codeList);
        sysDictDetailMapper.delete(queryWrapper);
        //删除主表
        sysDictMapper.deleteByIds(ids);
        //刷新redis缓存
        refreshCache();
    }

    /**
     * 检查是否存在，已存在返回true
     *
     * @param code
     * @param nowId 排除ID
     * @return
     */
    @Override
    public Boolean checkDictExist(String code, String nowId) {
        LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDict::getCode, code);
        wrapper.ne(ObjectUtil.isNotEmpty(nowId), SysDict::getId, nowId);
        boolean exists = sysDictMapper.exists(wrapper);
        return exists;
    }

    /**
     * 刷新字典缓存
     */
    @Override
    public void refreshCache() {
        CacheUtil.delBatch(RedisConstant.DICT + "*");
        List<SysDict> sysDictList = getSysDictList();
        LambdaQueryWrapper<SysDictDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictDetail::getStatus, CommonConstant.STATUS_NORMAL);
        wrapper.orderByAsc(SysDictDetail::getSort);
        List<SysDictDetail> sysDictDetailList = sysDictDetailMapper.selectList(wrapper);
        for (SysDict sysDict : sysDictList) {
            List<SysDictDetail> collect = sysDictDetailList.stream().filter(dictDetail -> sysDict.getCode().equals(dictDetail.getDictCode())).collect(Collectors.toList());
            CacheUtil.setValue(RedisConstant.DICT + sysDict.getCode(), collect);
        }
    }
}
