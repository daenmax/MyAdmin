package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.annotation.DataScope;
import cn.daenx.myadmin.common.constant.RedisConstant;
import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.utils.MyUtil;
import cn.daenx.myadmin.common.utils.RedisUtil;
import cn.daenx.myadmin.common.vo.ComStatusUpdVo;
import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.daenx.myadmin.system.po.SysApiLimit;
import cn.daenx.myadmin.system.vo.SysApiLimitAddVo;
import cn.daenx.myadmin.system.vo.SysApiLimitPageVo;
import cn.daenx.myadmin.system.vo.SysApiLimitUpdVo;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.mapper.SysApiLimitMapper;
import cn.daenx.myadmin.system.service.SysApiLimitService;

import java.util.List;

@Service
public class SysApiLimitServiceImpl extends ServiceImpl<SysApiLimitMapper, SysApiLimit> implements SysApiLimitService {
    @Resource
    private SysApiLimitMapper sysApiLimitMapper;

    private LambdaQueryWrapper<SysApiLimit> getWrapper(SysApiLimitPageVo vo) {
        LambdaQueryWrapper<SysApiLimit> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(ObjectUtil.isNotEmpty(vo.getApiName()), SysApiLimit::getApiName, vo.getApiName());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getApiUri()), SysApiLimit::getApiUri, vo.getApiUri());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getLimitType()), SysApiLimit::getLimitType, vo.getLimitType());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getRetMsg()), SysApiLimit::getRetMsg, vo.getRetMsg());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getStatus()), SysApiLimit::getStatus, vo.getStatus());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getRemark()), SysApiLimit::getRemark, vo.getRemark());
        String startTime = vo.getStartTime();
        String endTime = vo.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), SysApiLimit::getCreateTime, startTime, endTime);
        return wrapper;
    }


    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @DataScope(alias = "sys_api_limit")
    @Override
    public IPage<SysApiLimit> getPage(SysApiLimitPageVo vo) {
        LambdaQueryWrapper<SysApiLimit> wrapper = getWrapper(vo);
        Page<SysApiLimit> sysApiLimitPage = sysApiLimitMapper.selectPage(vo.getPage(true), wrapper);
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
     * @param vo
     */
    @Override
    public void addInfo(SysApiLimitAddVo vo) {
        if (SystemConstant.API_LIMIT_CURRENT.equals(vo.getLimitType())) {
            //限流
            if (ObjectUtil.isEmpty(vo.getSingleFrequency()) && ObjectUtil.isEmpty(vo.getWholeFrequency())) {
                throw new MyException("单个用户限制和全部用户限制最少填写一个");
            }
            if (ObjectUtil.isNotEmpty(vo.getSingleFrequency())) {
                if (ObjectUtil.isEmpty(vo.getSingleTime()) || ObjectUtil.isEmpty(vo.getSingleTimeUnit())) {
                    throw new MyException("单个用户限制填写不完整");
                }
                if (vo.getSingleFrequency() <= 0) {
                    throw new MyException("次数最小为1");
                }
                if (vo.getSingleTime() <= 0) {
                    throw new MyException("时间最小为1");
                }
            }
            if (ObjectUtil.isNotEmpty(vo.getWholeFrequency())) {
                if (ObjectUtil.isEmpty(vo.getWholeTime()) || ObjectUtil.isEmpty(vo.getWholeTimeUnit())) {
                    throw new MyException("全部用户限制填写不完整");
                }
                if (vo.getWholeFrequency() <= 0) {
                    throw new MyException("次数最小为1");
                }
                if (vo.getWholeTime() <= 0) {
                    throw new MyException("时间最小为1");
                }
            }
        }
        if (checkApiLimitExist(vo.getApiUri(), vo.getLimitType(), null)) {
            throw new MyException("当前接口已经存在当前限制类型");
        }
        SysApiLimit sysApiLimit = new SysApiLimit();
        sysApiLimit.setApiName(vo.getApiName());
        sysApiLimit.setApiUri(vo.getApiUri());
        sysApiLimit.setSingleFrequency(vo.getSingleFrequency());
        sysApiLimit.setSingleTime(vo.getSingleTime());
        sysApiLimit.setSingleTimeUnit(vo.getSingleTimeUnit());
        sysApiLimit.setWholeFrequency(vo.getWholeFrequency());
        sysApiLimit.setWholeTime(vo.getWholeTime());
        sysApiLimit.setWholeTimeUnit(vo.getWholeTimeUnit());
        sysApiLimit.setLimitType(vo.getLimitType());
        sysApiLimit.setRetMsg(vo.getRetMsg());
        sysApiLimit.setStatus(vo.getStatus());
        sysApiLimit.setRemark(vo.getRemark());
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
     * @param vo
     */
    @DataScope(alias = "sys_api_limit")
    @Override
    public void editInfo(SysApiLimitUpdVo vo) {
        if (SystemConstant.API_LIMIT_CURRENT.equals(vo.getLimitType())) {
            //限流
            if (ObjectUtil.isEmpty(vo.getSingleFrequency()) && ObjectUtil.isEmpty(vo.getWholeFrequency())) {
                throw new MyException("单个用户限制和全部用户限制最少填写一个");
            }
            if (ObjectUtil.isNotEmpty(vo.getSingleFrequency())) {
                if (ObjectUtil.isEmpty(vo.getSingleTime()) || ObjectUtil.isEmpty(vo.getSingleTimeUnit())) {
                    throw new MyException("单个用户限制填写不完整");
                }
                if (vo.getSingleFrequency() <= 0) {
                    throw new MyException("次数最小为1");
                }
                if (vo.getSingleTime() <= 0) {
                    throw new MyException("时间最小为1");
                }
            }
            if (ObjectUtil.isNotEmpty(vo.getWholeFrequency())) {
                if (ObjectUtil.isEmpty(vo.getWholeTime()) || ObjectUtil.isEmpty(vo.getWholeTimeUnit())) {
                    throw new MyException("全部用户限制填写不完整");
                }
                if (vo.getWholeFrequency() <= 0) {
                    throw new MyException("次数最小为1");
                }
                if (vo.getWholeTime() <= 0) {
                    throw new MyException("时间最小为1");
                }
            }
        }
        if (checkApiLimitExist(vo.getApiUri(), vo.getLimitType(), vo.getId())) {
            throw new MyException("当前接口已经存在当前限制类型");
        }
        SysApiLimit sysApiLimitOld = getInfo(vo.getId());
        if (ObjectUtil.isEmpty(sysApiLimitOld)) {
            throw new MyException("ID错误");
        }
        LambdaUpdateWrapper<SysApiLimit> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysApiLimit::getId, vo.getId());
        updateWrapper.set(SysApiLimit::getApiName, vo.getApiName());
        updateWrapper.set(SysApiLimit::getApiUri, vo.getApiUri());
        updateWrapper.set(SysApiLimit::getSingleFrequency, vo.getSingleFrequency());
        updateWrapper.set(SysApiLimit::getSingleTime, vo.getSingleTime());
        updateWrapper.set(SysApiLimit::getSingleTimeUnit, vo.getSingleTimeUnit());
        updateWrapper.set(SysApiLimit::getWholeFrequency, vo.getWholeFrequency());
        updateWrapper.set(SysApiLimit::getWholeTime, vo.getWholeTime());
        updateWrapper.set(SysApiLimit::getWholeTimeUnit, vo.getWholeTimeUnit());
        updateWrapper.set(SysApiLimit::getLimitType, vo.getLimitType());
        updateWrapper.set(SysApiLimit::getRetMsg, vo.getRetMsg());
        updateWrapper.set(SysApiLimit::getStatus, vo.getStatus());
        updateWrapper.set(SysApiLimit::getRemark, vo.getRemark());
        int rows = sysApiLimitMapper.update(new SysApiLimit(), updateWrapper);
        if (rows < 1) {
            throw new MyException("修改失败");
        }
        SysApiLimit sysApiLimitNew = getInfo(vo.getId());
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
     * @param vo
     */
    @DataScope(alias = "sys_api_limit")
    @Override
    public void changeStatus(ComStatusUpdVo vo) {
        LambdaUpdateWrapper<SysApiLimit> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysApiLimit::getId, vo.getId());
        updateWrapper.set(SysApiLimit::getStatus, vo.getStatus());
        int rows = sysApiLimitMapper.update(new SysApiLimit(), updateWrapper);
        if (rows < 1) {
            throw new MyException("修改失败");
        }
        SysApiLimit sysApiLimit = getInfo(vo.getId());
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
        RedisUtil.delBatch(RedisConstant.API_LIMIT_SINGLE_KEY + "*");
        RedisUtil.delBatch(RedisConstant.API_LIMIT_WHOLE_KEY + "*");
        RedisUtil.delBatch(RedisConstant.API_LIMIT_CLOSE_KEY + "*");
        LambdaQueryWrapper<SysApiLimit> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysApiLimit::getStatus, SystemConstant.STATUS_NORMAL);
        List<SysApiLimit> list = sysApiLimitMapper.selectList(wrapper);
        for (SysApiLimit sysApiLimit : list) {
            initApiLimit(sysApiLimit);
        }
    }

    private void initApiLimit(SysApiLimit sysApiLimit) {
        if (sysApiLimit.getLimitType().equals(SystemConstant.API_LIMIT_CURRENT)) {
            //限流
            cleanCache(sysApiLimit);
            if (!SystemConstant.STATUS_NORMAL.equals(sysApiLimit.getStatus())) {
                return;
            }
            if (ObjectUtil.isNotEmpty(sysApiLimit.getSingleFrequency()) && ObjectUtil.isNotEmpty(sysApiLimit.getSingleTime()) && ObjectUtil.isNotEmpty(sysApiLimit.getSingleTimeUnit())) {
                //转换成秒
                Integer singleSecond = MyUtil.toSecond(sysApiLimit.getSingleTime(), sysApiLimit.getSingleTimeUnit());
                String singleKey = RedisConstant.API_LIMIT_SINGLE_KEY + sysApiLimit.getApiUri();
                BoundHashOperations<String, String, Object> hashOps = RedisUtil.getRedisTemplate().boundHashOps(singleKey);
                hashOps.put("max", sysApiLimit.getSingleFrequency());
                hashOps.put("outTime", singleSecond);
            }
            if (ObjectUtil.isNotEmpty(sysApiLimit.getWholeFrequency()) && ObjectUtil.isNotEmpty(sysApiLimit.getWholeTime()) && ObjectUtil.isNotEmpty(sysApiLimit.getWholeTimeUnit())) {
                //转换成秒
                Integer wholeSecond = MyUtil.toSecond(sysApiLimit.getWholeTime(), sysApiLimit.getWholeTimeUnit());
                String wholeKey = RedisConstant.API_LIMIT_WHOLE_KEY + sysApiLimit.getApiUri();
                BoundHashOperations<String, String, Object> hashOps = RedisUtil.getRedisTemplate().boundHashOps(wholeKey);
                hashOps.put("max", sysApiLimit.getWholeFrequency());
                hashOps.put("outTime", wholeSecond);
            }
        } else {
            //停用
            cleanCache(sysApiLimit);
            if (!SystemConstant.STATUS_NORMAL.equals(sysApiLimit.getStatus())) {
                return;
            }
            RedisUtil.setValue(RedisConstant.API_LIMIT_CLOSE_KEY + sysApiLimit.getApiUri(), sysApiLimit);
        }
    }

    private void cleanCache(SysApiLimit sysApiLimit) {
        if (sysApiLimit.getLimitType().equals(SystemConstant.API_LIMIT_CURRENT)) {
            //限流
            //单个用户配置
            RedisUtil.del(RedisConstant.API_LIMIT_SINGLE_KEY + sysApiLimit.getApiUri());
            //单个用户记录
            RedisUtil.delBatch(RedisConstant.API_LIMIT_SINGLE_KEY + sysApiLimit.getApiUri() + ":*");
            //全部用户配置
            RedisUtil.del(RedisConstant.API_LIMIT_WHOLE_KEY + sysApiLimit.getApiUri());
            //全部用户记录
            RedisUtil.del(RedisConstant.API_LIMIT_WHOLE_LIMITER_KEY + sysApiLimit.getApiUri());
        } else {
            //停用
            RedisUtil.del(RedisConstant.API_LIMIT_CLOSE_KEY + sysApiLimit.getApiUri());
        }
    }
}
