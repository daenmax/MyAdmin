package cn.daenx.modules.system.service.impl;

import cn.daenx.framework.cache.utils.CacheUtil;
import cn.daenx.framework.common.constant.CommonConstant;
import cn.daenx.framework.common.constant.RedisConstant;
import cn.daenx.framework.common.constant.SystemConstant;
import cn.daenx.framework.common.domain.dto.ComStatusUpdDto;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.utils.MyUtil;
import cn.daenx.framework.dataScope.annotation.DataScope;
import cn.daenx.modules.system.domain.dto.sysApiLimit.SysApiLimitAddDto;
import cn.daenx.modules.system.domain.dto.sysApiLimit.SysApiLimitPageDto;
import cn.daenx.modules.system.domain.dto.sysApiLimit.SysApiLimitUpdDto;
import cn.daenx.modules.system.domain.po.SysApiLimit;
import cn.daenx.modules.system.mapper.SysApiLimitMapper;
import cn.daenx.modules.system.service.SysApiLimitService;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysApiLimitServiceImpl extends ServiceImpl<SysApiLimitMapper, SysApiLimit> implements SysApiLimitService {
    @Resource
    private SysApiLimitMapper sysApiLimitMapper;

    private LambdaQueryWrapper<SysApiLimit> getWrapper(SysApiLimitPageDto dto) {
        LambdaQueryWrapper<SysApiLimit> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(ObjectUtil.isNotEmpty(dto.getApiName()), SysApiLimit::getApiName, dto.getApiName());
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getApiUri()), SysApiLimit::getApiUri, dto.getApiUri());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getLimitType()), SysApiLimit::getLimitType, dto.getLimitType());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getRetMsg()), SysApiLimit::getRetMsg, dto.getRetMsg());
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getStatus()), SysApiLimit::getStatus, dto.getStatus());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getRemark()), SysApiLimit::getRemark, dto.getRemark());
        String startTime = dto.getStartTime();
        String endTime = dto.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), SysApiLimit::getCreateTime, startTime, endTime);
        return wrapper;
    }


    /**
     * 分页列表
     *
     * @param dto
     * @return
     */
    @DataScope(alias = "sys_api_limit")
    @Override
    public IPage<SysApiLimit> getPage(SysApiLimitPageDto dto) {
        LambdaQueryWrapper<SysApiLimit> wrapper = getWrapper(dto);
        Page<SysApiLimit> sysApiLimitPage = sysApiLimitMapper.selectPage(dto.getPage(true), wrapper);
        return sysApiLimitPage;
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @DataScope(alias = "sys_api_limit")
    @Override
    public SysApiLimit getInfo(String id) {
        SysApiLimit sysApiLimit = sysApiLimitMapper.selectById(id);
        return sysApiLimit;
    }

    /**
     * 新增
     *
     * @param dto
     */
    @Override
    public void addInfo(SysApiLimitAddDto dto) {
        if (SystemConstant.API_LIMIT_CURRENT.equals(dto.getLimitType())) {
            //限流
            if (ObjectUtil.isEmpty(dto.getSingleFrequency()) && ObjectUtil.isEmpty(dto.getWholeFrequency())) {
                throw new MyException("单个用户限制和全部用户限制最少填写一个");
            }
            if (ObjectUtil.isNotEmpty(dto.getSingleFrequency())) {
                if (ObjectUtil.isEmpty(dto.getSingleTime()) || ObjectUtil.isEmpty(dto.getSingleTimeUnit())) {
                    throw new MyException("单个用户限制填写不完整");
                }
                if (dto.getSingleFrequency() <= 0) {
                    throw new MyException("次数最小为1");
                }
                if (dto.getSingleTime() <= 0) {
                    throw new MyException("时间最小为1");
                }
            }
            if (ObjectUtil.isNotEmpty(dto.getWholeFrequency())) {
                if (ObjectUtil.isEmpty(dto.getWholeTime()) || ObjectUtil.isEmpty(dto.getWholeTimeUnit())) {
                    throw new MyException("全部用户限制填写不完整");
                }
                if (dto.getWholeFrequency() <= 0) {
                    throw new MyException("次数最小为1");
                }
                if (dto.getWholeTime() <= 0) {
                    throw new MyException("时间最小为1");
                }
            }
        }
        if (checkApiLimitExist(dto.getApiUri(), dto.getLimitType(), null)) {
            throw new MyException("当前接口已经存在当前限制类型");
        }
        SysApiLimit sysApiLimit = new SysApiLimit();
        sysApiLimit.setApiName(dto.getApiName());
        sysApiLimit.setApiUri(dto.getApiUri());
        sysApiLimit.setSingleFrequency(dto.getSingleFrequency());
        sysApiLimit.setSingleTime(dto.getSingleTime());
        sysApiLimit.setSingleTimeUnit(dto.getSingleTimeUnit());
        sysApiLimit.setWholeFrequency(dto.getWholeFrequency());
        sysApiLimit.setWholeTime(dto.getWholeTime());
        sysApiLimit.setWholeTimeUnit(dto.getWholeTimeUnit());
        sysApiLimit.setLimitType(dto.getLimitType());
        sysApiLimit.setRetMsg(dto.getRetMsg());
        sysApiLimit.setStatus(dto.getStatus());
        sysApiLimit.setRemark(dto.getRemark());
        int insert = sysApiLimitMapper.insert(sysApiLimit);
        if (insert < 1) {
            throw new MyException("新增失败");
        }
        SysApiLimit sysApiLimitNew = getInfo(sysApiLimit.getId());
        //刷新redis缓存
        initApiLimit(sysApiLimitNew);
    }

    /**
     * 修改
     *
     * @param dto
     */
    @DataScope(alias = "sys_api_limit")
    @Override
    public void editInfo(SysApiLimitUpdDto dto) {
        if (SystemConstant.API_LIMIT_CURRENT.equals(dto.getLimitType())) {
            //限流
            if (ObjectUtil.isEmpty(dto.getSingleFrequency()) && ObjectUtil.isEmpty(dto.getWholeFrequency())) {
                throw new MyException("单个用户限制和全部用户限制最少填写一个");
            }
            if (ObjectUtil.isNotEmpty(dto.getSingleFrequency())) {
                if (ObjectUtil.isEmpty(dto.getSingleTime()) || ObjectUtil.isEmpty(dto.getSingleTimeUnit())) {
                    throw new MyException("单个用户限制填写不完整");
                }
                if (dto.getSingleFrequency() <= 0) {
                    throw new MyException("次数最小为1");
                }
                if (dto.getSingleTime() <= 0) {
                    throw new MyException("时间最小为1");
                }
            }
            if (ObjectUtil.isNotEmpty(dto.getWholeFrequency())) {
                if (ObjectUtil.isEmpty(dto.getWholeTime()) || ObjectUtil.isEmpty(dto.getWholeTimeUnit())) {
                    throw new MyException("全部用户限制填写不完整");
                }
                if (dto.getWholeFrequency() <= 0) {
                    throw new MyException("次数最小为1");
                }
                if (dto.getWholeTime() <= 0) {
                    throw new MyException("时间最小为1");
                }
            }
        }
        if (checkApiLimitExist(dto.getApiUri(), dto.getLimitType(), dto.getId())) {
            throw new MyException("当前接口已经存在当前限制类型");
        }
        SysApiLimit sysApiLimitOld = getInfo(dto.getId());
        if (ObjectUtil.isEmpty(sysApiLimitOld)) {
            throw new MyException("ID错误");
        }
        LambdaUpdateWrapper<SysApiLimit> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysApiLimit::getId, dto.getId());
        updateWrapper.set(SysApiLimit::getApiName, dto.getApiName());
        updateWrapper.set(SysApiLimit::getApiUri, dto.getApiUri());
        updateWrapper.set(SysApiLimit::getSingleFrequency, dto.getSingleFrequency());
        updateWrapper.set(SysApiLimit::getSingleTime, dto.getSingleTime());
        updateWrapper.set(SysApiLimit::getSingleTimeUnit, dto.getSingleTimeUnit());
        updateWrapper.set(SysApiLimit::getWholeFrequency, dto.getWholeFrequency());
        updateWrapper.set(SysApiLimit::getWholeTime, dto.getWholeTime());
        updateWrapper.set(SysApiLimit::getWholeTimeUnit, dto.getWholeTimeUnit());
        updateWrapper.set(SysApiLimit::getLimitType, dto.getLimitType());
        updateWrapper.set(SysApiLimit::getRetMsg, dto.getRetMsg());
        updateWrapper.set(SysApiLimit::getStatus, dto.getStatus());
        updateWrapper.set(SysApiLimit::getRemark, dto.getRemark());
        int rows = sysApiLimitMapper.update(new SysApiLimit(), updateWrapper);
        if (rows < 1) {
            throw new MyException("修改失败");
        }
        SysApiLimit sysApiLimitNew = getInfo(dto.getId());
        if (!sysApiLimitOld.getLimitType().equals(sysApiLimitNew.getLimitType()) || !sysApiLimitOld.getApiUri().equals(sysApiLimitNew.getApiUri())) {
            //修改了限制类型或者接口URI，需要清除原来的
            cleanCache(sysApiLimitOld);
        }
        //刷新redis缓存
        initApiLimit(sysApiLimitNew);
    }

    /**
     * 修改状态
     *
     * @param dto
     */
    @DataScope(alias = "sys_api_limit")
    @Override
    public void changeStatus(ComStatusUpdDto dto) {
        LambdaUpdateWrapper<SysApiLimit> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysApiLimit::getId, dto.getId());
        updateWrapper.set(SysApiLimit::getStatus, dto.getStatus());
        int rows = sysApiLimitMapper.update(new SysApiLimit(), updateWrapper);
        if (rows < 1) {
            throw new MyException("修改失败");
        }
        SysApiLimit sysApiLimit = getInfo(dto.getId());
        //刷新redis缓存
        initApiLimit(sysApiLimit);
    }

    /**
     * 删除
     *
     * @param ids
     */
    @Override
    public void deleteByIds(List<String> ids) {
        for (String id : ids) {
            SysApiLimit sysApiLimit = getInfo(id);
            int i = sysApiLimitMapper.deleteById(sysApiLimit);
            if (i > 0) {
                cleanCache(sysApiLimit);
            }
        }
    }

    /**
     * 检查某个接口的某个限制是否存在，已存在返回true
     *
     * @param apiUri
     * @param limitType
     * @param nowId     排除ID
     * @return
     */
    @Override
    public Boolean checkApiLimitExist(String apiUri, String limitType, String nowId) {
        LambdaQueryWrapper<SysApiLimit> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysApiLimit::getApiUri, apiUri);
        wrapper.eq(SysApiLimit::getLimitType, limitType);
        wrapper.ne(ObjectUtil.isNotEmpty(nowId), SysApiLimit::getId, nowId);
        boolean exists = sysApiLimitMapper.exists(wrapper);
        return exists;
    }

    /**
     * 刷新redis缓存
     */
    @Override
    public void refreshApiLimitCache() {
        CacheUtil.delBatch(RedisConstant.API_LIMIT_SINGLE_KEY + "*");
        CacheUtil.delBatch(RedisConstant.API_LIMIT_WHOLE_KEY + "*");
        CacheUtil.delBatch(RedisConstant.API_LIMIT_CLOSE_KEY + "*");
        LambdaQueryWrapper<SysApiLimit> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysApiLimit::getStatus, CommonConstant.STATUS_NORMAL);
        List<SysApiLimit> list = sysApiLimitMapper.selectList(wrapper);
        for (SysApiLimit sysApiLimit : list) {
            initApiLimit(sysApiLimit);
        }
    }

    private void initApiLimit(SysApiLimit sysApiLimit) {
        if (!"redis".equals(CacheUtil.getType())) {
            return;
        }
        if (sysApiLimit.getLimitType().equals(SystemConstant.API_LIMIT_CURRENT)) {
            //限流
            cleanCache(sysApiLimit);
            if (!CommonConstant.STATUS_NORMAL.equals(sysApiLimit.getStatus())) {
                return;
            }
            if (ObjectUtil.isNotEmpty(sysApiLimit.getSingleFrequency()) && ObjectUtil.isNotEmpty(sysApiLimit.getSingleTime()) && ObjectUtil.isNotEmpty(sysApiLimit.getSingleTimeUnit())) {
                //转换成秒
                Integer singleSecond = MyUtil.toSecond(sysApiLimit.getSingleTime(), sysApiLimit.getSingleTimeUnit());
                String singleKey = RedisConstant.API_LIMIT_SINGLE_KEY + sysApiLimit.getApiUri();
                BoundHashOperations<String, String, Object> hashOps = CacheUtil.getRedisTemplate().boundHashOps(singleKey);
                hashOps.put("max", sysApiLimit.getSingleFrequency());
                hashOps.put("outTime", singleSecond);
            }
            if (ObjectUtil.isNotEmpty(sysApiLimit.getWholeFrequency()) && ObjectUtil.isNotEmpty(sysApiLimit.getWholeTime()) && ObjectUtil.isNotEmpty(sysApiLimit.getWholeTimeUnit())) {
                //转换成秒
                Integer wholeSecond = MyUtil.toSecond(sysApiLimit.getWholeTime(), sysApiLimit.getWholeTimeUnit());
                String wholeKey = RedisConstant.API_LIMIT_WHOLE_KEY + sysApiLimit.getApiUri();
                BoundHashOperations<String, String, Object> hashOps = CacheUtil.getRedisTemplate().boundHashOps(wholeKey);
                hashOps.put("max", sysApiLimit.getWholeFrequency());
                hashOps.put("outTime", wholeSecond);
            }
        } else {
            //停用
            cleanCache(sysApiLimit);
            if (!CommonConstant.STATUS_NORMAL.equals(sysApiLimit.getStatus())) {
                return;
            }
            CacheUtil.setValue(RedisConstant.API_LIMIT_CLOSE_KEY + sysApiLimit.getApiUri(), sysApiLimit);
        }
    }

    private void cleanCache(SysApiLimit sysApiLimit) {
        if (!"redis".equals(CacheUtil.getType())) {
            return;
        }
        if (sysApiLimit.getLimitType().equals(SystemConstant.API_LIMIT_CURRENT)) {
            //限流
            //单个用户配置
            CacheUtil.del(RedisConstant.API_LIMIT_SINGLE_KEY + sysApiLimit.getApiUri());
            //单个用户记录
            CacheUtil.delBatch(RedisConstant.API_LIMIT_SINGLE_KEY + sysApiLimit.getApiUri() + ":*");
            //全部用户配置
            CacheUtil.del(RedisConstant.API_LIMIT_WHOLE_KEY + sysApiLimit.getApiUri());
            //全部用户记录
            CacheUtil.del(RedisConstant.API_LIMIT_WHOLE_LIMITER_KEY + sysApiLimit.getApiUri());
        } else {
            //停用
            CacheUtil.del(RedisConstant.API_LIMIT_CLOSE_KEY + sysApiLimit.getApiUri());
        }
    }
}
