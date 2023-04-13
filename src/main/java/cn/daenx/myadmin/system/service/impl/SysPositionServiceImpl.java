package cn.daenx.myadmin.system.service.impl;

import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.system.mapper.SysPositionUserMapper;
import cn.daenx.myadmin.system.po.SysPositionUser;
import cn.daenx.myadmin.system.service.LoginUtilService;
import cn.daenx.myadmin.system.vo.SysPositionAddVo;
import cn.daenx.myadmin.system.vo.SysPositionPageVo;
import cn.daenx.myadmin.system.vo.SysPositionUpdVo;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.myadmin.system.mapper.SysPositionMapper;
import cn.daenx.myadmin.system.po.SysPosition;
import cn.daenx.myadmin.system.service.SysPositionService;

import java.util.List;

@Service
public class SysPositionServiceImpl extends ServiceImpl<SysPositionMapper, SysPosition> implements SysPositionService {

    @Resource
    private SysPositionMapper sysPositionMapper;
    @Resource
    private SysPositionUserMapper sysPositionUserMapper;
    @Resource
    private LoginUtilService loginUtilService;

    @Override
    public List<SysPosition> getSysPositionListByUserId(String userId) {
        return sysPositionMapper.getSysPositionListByUserId(userId);
    }

    private LambdaQueryWrapper<SysPosition> getWrapper(SysPositionPageVo vo) {
        LambdaQueryWrapper<SysPosition> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(ObjectUtil.isNotEmpty(vo.getName()), SysPosition::getName, vo.getName());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getCode()), SysPosition::getCode, vo.getCode());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getSummary()), SysPosition::getSummary, vo.getSummary());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getStatus()), SysPosition::getStatus, vo.getStatus());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getRemark()), SysPosition::getRemark, vo.getRemark());
        String startTime = vo.getStartTime();
        String endTime = vo.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), SysPosition::getCreateTime, startTime, endTime);
        wrapper.orderByAsc(SysPosition::getSort);
        return wrapper;
    }

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    @Override
    public IPage<SysPosition> getPage(SysPositionPageVo vo) {
        LambdaQueryWrapper<SysPosition> wrapper = getWrapper(vo);
        Page<SysPosition> sysPositionPage = sysPositionMapper.selectPage(vo.getPage(false), wrapper);
        return sysPositionPage;
    }

    /**
     * 获取所有列表，用于导出
     *
     * @param vo
     * @return
     */
    @Override
    public List<SysPosition> getAll(SysPositionPageVo vo) {
        LambdaQueryWrapper<SysPosition> wrapper = getWrapper(vo);
        List<SysPosition> sysPositions = sysPositionMapper.selectList(wrapper);
        return sysPositions;
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @Override
    public SysPosition getInfo(String id) {
        return sysPositionMapper.selectById(id);
    }

    /**
     * 检查是否存在，已存在返回true
     *
     * @param code
     * @param nowId 排除ID
     * @return
     */
    @Override
    public Boolean checkCodeExist(String code, String nowId) {
        LambdaQueryWrapper<SysPosition> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysPosition::getCode, code);
        wrapper.ne(ObjectUtil.isNotEmpty(nowId), SysPosition::getId, nowId);
        boolean exists = sysPositionMapper.exists(wrapper);
        return exists;
    }

    /**
     * 检查是否存在，已存在返回true
     *
     * @param name
     * @param nowId 排除ID
     * @return
     */
    @Override
    public Boolean checkNameExist(String name, String nowId) {
        LambdaQueryWrapper<SysPosition> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysPosition::getName, name);
        wrapper.ne(ObjectUtil.isNotEmpty(nowId), SysPosition::getId, nowId);
        boolean exists = sysPositionMapper.exists(wrapper);
        return exists;
    }

    /**
     * 修改
     *
     * @param vo
     */
    @Override
    public void editInfo(SysPositionUpdVo vo) {
        if (checkCodeExist(vo.getCode(), vo.getId())) {
            throw new MyException("岗位编码已存在");
        }
        if (checkNameExist(vo.getName(), vo.getId())) {
            throw new MyException("岗位名称已存在");
        }
        LambdaUpdateWrapper<SysPosition> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysPosition::getId, vo.getId());
        wrapper.set(SysPosition::getName, vo.getName());
        wrapper.set(SysPosition::getCode, vo.getCode());
        wrapper.set(SysPosition::getSummary, vo.getSummary());
        wrapper.set(SysPosition::getSort, vo.getSort());
        wrapper.set(SysPosition::getStatus, vo.getStatus());
        wrapper.set(SysPosition::getRemark, vo.getRemark());
        int rows = sysPositionMapper.update(new SysPosition(), wrapper);
        if (rows < 1) {
            throw new MyException("修改失败");
        }
        //下线相关用户
        loginUtilService.logoutByPositionId(vo.getId());
    }

    /**
     * 新增
     *
     * @param vo
     */
    @Override
    public void addInfo(SysPositionAddVo vo) {
        if (checkCodeExist(vo.getCode(), null)) {
            throw new MyException("岗位编码已存在");
        }
        if (checkNameExist(vo.getName(), null)) {
            throw new MyException("岗位名称已存在");
        }
        SysPosition sysPosition = new SysPosition();
        sysPosition.setName(vo.getName());
        sysPosition.setCode(vo.getCode());
        sysPosition.setSummary(vo.getSummary());
        sysPosition.setSort(vo.getSort());
        sysPosition.setStatus(vo.getStatus());
        sysPosition.setRemark(vo.getRemark());
        int insert = sysPositionMapper.insert(sysPosition);
        if (insert < 1) {
            throw new MyException("新增失败");
        }
    }

    /**
     * 删除
     *
     * @param ids
     */
    @Override
    public void deleteByIds(List<String> ids) {
        for (String id : ids) {
            if (getPositionUserCount(id) > 0) {
                SysPosition info = getInfo(id);
                throw new MyException("岗位[" + info.getName() + "]已经被分配给用户，请先处理分配关系");
            }
        }
        int i = sysPositionMapper.deleteBatchIds(ids);
        if (i < 1) {
            throw new MyException("删除失败");
        }
        //下线相关用户
        loginUtilService.logoutByPositionIds(ids);
    }

    /**
     * 获取指定岗位的用户数量
     *
     * @param id
     * @return
     */
    private Long getPositionUserCount(String id) {
        LambdaQueryWrapper<SysPositionUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysPositionUser::getPositionId, id);
        Long count = sysPositionUserMapper.selectCount(wrapper);
        return count;
    }
}
