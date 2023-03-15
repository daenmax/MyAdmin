package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.annotation.DataScope;
import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.utils.RedisUtil;
import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.daenx.myadmin.system.mapper.SysDictDetailMapper;
import cn.daenx.myadmin.system.po.SysDictDetail;
import cn.daenx.myadmin.system.vo.SysDictAddVo;
import cn.daenx.myadmin.system.vo.SysDictPageVo;
import cn.daenx.myadmin.system.vo.SysDictUpdVo;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.mapper.SysDictMapper;
import cn.daenx.myadmin.system.po.SysDict;
import cn.daenx.myadmin.system.service.SysDictService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private SysDictMapper sysDictMapper;
    @Resource
    private SysDictDetailMapper sysDictDetailMapper;

    @Override
    public List<SysDict> getSysDictList() {
        LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDict::getStatus, SystemConstant.STATUS_NORMAL);
        List<SysDict> sysDictList = sysDictMapper.selectList(wrapper);
        return sysDictList;
    }

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @Override
    public IPage<SysDict> getPage(SysDictPageVo vo) {
        LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(ObjectUtil.isNotEmpty(vo.getName()), SysDict::getName, vo.getName());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getCode()), SysDict::getCode, vo.getCode());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getStatus()), SysDict::getStatus, vo.getStatus());
        String startTime = vo.getStartTime();
        String endTime = vo.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), SysDict::getCreateTime, startTime, endTime);
        Page<SysDict> sysDictPage = sysDictMapper.selectPage(vo.getPage(), wrapper);
        return sysDictPage;
    }

    /**
     * 获取所有列表，用于导出
     *
     * @param vo
     * @return
     */
    @Override
    public List<SysDict> getAll(SysDictPageVo vo) {
        LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(ObjectUtil.isNotEmpty(vo.getName()), SysDict::getName, vo.getName());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getCode()), SysDict::getCode, vo.getCode());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getStatus()), SysDict::getStatus, vo.getStatus());
        String startTime = vo.getStartTime();
        String endTime = vo.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), SysDict::getCreateTime, startTime, endTime);
        List<SysDict> sysDictList = sysDictMapper.selectList(wrapper);
        return sysDictList;
    }

    /**
     * +
     * 新增
     *
     * @param vo
     */
    @Override
    public void addInfo(SysDictAddVo vo) {
        if (checkDictExist(vo.getCode(), null)) {
            throw new MyException("字典编码已存在");
        }
        SysDict sysDict = new SysDict();
        sysDict.setCode(vo.getCode());
        sysDict.setName(vo.getName());
        sysDict.setStatus(vo.getStatus());
        sysDict.setRemark(vo.getRemark());
        int insert = sysDictMapper.insert(sysDict);
        if (insert > 0) {
            //TODO 刷新redis缓存
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
     * @param vo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editInfo(SysDictUpdVo vo) {
        if (checkDictExist(vo.getCode(), vo.getId())) {
            throw new MyException("字典编码已存在");
        }
        SysDict info = getInfo(vo.getId());
        LambdaUpdateWrapper<SysDict> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysDict::getId, vo.getId());
        wrapper.set(SysDict::getName, vo.getName());
        wrapper.set(SysDict::getCode, vo.getCode());
        wrapper.set(SysDict::getStatus, vo.getStatus());
        wrapper.set(SysDict::getRemark, vo.getRemark());
        int rows = sysDictMapper.update(null, wrapper);
        if (rows > 0) {
            LambdaQueryWrapper<SysDictDetail> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysDictDetail::getDictCode, info.getCode());
            Long count = sysDictDetailMapper.selectCount(queryWrapper);
            if (count > 0) {
                //修改字典明细
                LambdaUpdateWrapper<SysDictDetail> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.set(SysDictDetail::getDictCode, vo.getCode());
                updateWrapper.eq(SysDictDetail::getDictCode, info.getCode());
                int update = sysDictDetailMapper.update(null, updateWrapper);
                if (update == 0) {
                    throw new MyException("修改字典明细失败");
                }
            }
            //TODO 刷新redis缓存
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
    public void deleteByIds(String[] ids) {
        List<String> idList = Arrays.asList(ids);
        //删除明细
        LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SysDict::getId, idList);
        List<SysDict> sysDictList = sysDictMapper.selectList(wrapper);
        List<String> codeList = sysDictList.stream().map(SysDict::getCode).collect(Collectors.toList());
        LambdaQueryWrapper<SysDictDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysDictDetail::getDictCode, codeList);
        sysDictDetailMapper.delete(queryWrapper);
        //删除主表
        sysDictMapper.deleteBatchIds(idList);
        //TODO 刷新redis缓存
    }

    /**
     * 检查是否已存在，已存在返回true
     *
     * @param code
     * @param nowId 如果是修改时，那么需要传此值
     * @return
     */
    private Boolean checkDictExist(String code, String nowId) {
        LambdaUpdateWrapper<SysDict> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysDict::getCode, code);
        wrapper.ne(ObjectUtil.isNotEmpty(nowId), SysDict::getId, nowId);
        boolean exists = sysDictMapper.exists(wrapper);
        return exists;
    }

}
