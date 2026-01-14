package cn.daenx.modules.test.service.impl;

import cn.daenx.framework.common.constant.CommonConstant;
import cn.daenx.framework.common.domain.dto.ComStatusUpdDto;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.utils.MyUtil;
import cn.daenx.framework.dataScope.annotation.DataScope;
import cn.daenx.modules.test.domain.dto.testData.TestDataTreeAddDto;
import cn.daenx.modules.test.domain.dto.testData.TestDataTreePageDto;
import cn.daenx.modules.test.domain.dto.testData.TestDataTreeUpdDto;
import cn.daenx.modules.test.domain.po.TestDataTree;
import cn.daenx.modules.test.mapper.TestDataTreeMapper;
import cn.daenx.modules.test.service.TestDataTreeService;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestDataTreeServiceImpl extends ServiceImpl<TestDataTreeMapper, TestDataTree> implements TestDataTreeService {
    @Resource
    private TestDataTreeMapper testDataTreeMapper;

    private LambdaQueryWrapper<TestDataTree> getWrapper(TestDataTreePageDto dto) {
        LambdaQueryWrapper<TestDataTree> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getTitle()), TestDataTree::getTitle, dto.getTitle());
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getType()), TestDataTree::getType, dto.getType());
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getStatus()), TestDataTree::getStatus, dto.getStatus());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getContent()), TestDataTree::getContent, dto.getContent());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getRemark()), TestDataTree::getRemark, dto.getRemark());
        String startTime = dto.getStartTime();
        String endTime = dto.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), TestDataTree::getCreateTime, startTime, endTime);
        return wrapper;
    }

    /**
     * 获取列表
     *
     * @param dto
     * @return
     */
    @DataScope(alias = "test_data_tree")
    @Override
    public List<TestDataTree> getAll(TestDataTreePageDto dto) {
        LambdaQueryWrapper<TestDataTree> wrapper = getWrapper(dto);
        List<TestDataTree> list = testDataTreeMapper.selectList(wrapper);
        return list;
    }

    /**
     * 新增
     *
     * @param dto
     */
    @Override
    public void addInfo(TestDataTreeAddDto dto) {
        TestDataTree testDataTree = new TestDataTree();
        testDataTree.setParentId(dto.getParentId());
        testDataTree.setTitle(dto.getTitle());
        testDataTree.setContent(dto.getContent());
        testDataTree.setType(dto.getType());
        testDataTree.setStatus(dto.getStatus());
        testDataTree.setRemark(dto.getRemark());
        int insert = testDataTreeMapper.insert(testDataTree);
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
    @Override
    public TestDataTree getInfo(String id) {
        return testDataTreeMapper.selectById(id);
    }

    /**
     * 修改
     *
     * @param dto
     */
    @DataScope(alias = "test_data_tree")
    @Override
    public void editInfo(TestDataTreeUpdDto dto) {
        LambdaUpdateWrapper<TestDataTree> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(TestDataTree::getId, dto.getId());
        updateWrapper.set(TestDataTree::getParentId, dto.getParentId());
        updateWrapper.set(TestDataTree::getTitle, dto.getTitle());
        updateWrapper.set(TestDataTree::getContent, dto.getContent());
        updateWrapper.set(TestDataTree::getType, dto.getType());
        updateWrapper.set(TestDataTree::getStatus, dto.getStatus());
        updateWrapper.set(TestDataTree::getRemark, dto.getRemark());
        int rows = testDataTreeMapper.update(new TestDataTree(), updateWrapper);
        if (rows < 1) {
            throw new MyException("修改失败");
        }
    }

    private Boolean checkHasChildren(String id) {
        LambdaQueryWrapper<TestDataTree> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TestDataTree::getParentId, id);
        boolean exists = testDataTreeMapper.exists(queryWrapper);
        return exists;
    }

    /**
     * 删除
     *
     * @param id
     */
    @DataScope(alias = "test_data_tree")
    @Override
    public void deleteById(String id) {
        if (checkHasChildren(id)) {
            throw new MyException("存在子数据，请先删除子数据");
        }
        int i = testDataTreeMapper.deleteById(id);
        if (i < 1) {
            throw new MyException("删除失败");
        }
    }

    /**
     * 修改状态
     *
     * @param dto
     */
    @DataScope(alias = "test_data_tree")
    @Override
    public void changeStatus(ComStatusUpdDto dto) {
        LambdaUpdateWrapper<TestDataTree> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(TestDataTree::getId, dto.getId());
        updateWrapper.set(TestDataTree::getStatus, dto.getStatus());
        int rows = testDataTreeMapper.update(new TestDataTree(), updateWrapper);
        if (rows < 1) {
            throw new MyException("修改失败");
        }
        if (dto.getStatus().equals(CommonConstant.STATUS_DISABLE)) {
            //禁用所有子级
            List<TestDataTree> list = testDataTreeMapper.selectList(new LambdaQueryWrapper<>());
            List<TestDataTree> retList = handleListByParentId(list, dto.getId());
            List<String> idList = MyUtil.joinToList(retList, TestDataTree::getId);
            LambdaUpdateWrapper<TestDataTree> updateWrapper2 = new LambdaUpdateWrapper<>();
            updateWrapper2.in(TestDataTree::getId, idList);
            updateWrapper2.set(TestDataTree::getStatus, CommonConstant.STATUS_DISABLE);
            testDataTreeMapper.update(new TestDataTree(), updateWrapper2);
        }
    }

    private List<TestDataTree> handleListByParentId(List<TestDataTree> list, String id) {
        List<TestDataTree> retList = new ArrayList<>();
        List<TestDataTree> collect = list.stream().filter(item -> id.equals(item.getParentId())).collect(Collectors.toList());
        if (collect.size() > 0) {
            retList.addAll(collect);
        }
        while (collect.size() > 0) {
            List<String> idList = MyUtil.joinToList(collect, TestDataTree::getId);
            collect = list.stream().filter(item -> idList.contains(item.getParentId())).collect(Collectors.toList());
            if (collect.size() > 0) {
                retList.addAll(collect);
            }
        }
        return retList;
    }
}
