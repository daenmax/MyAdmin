package cn.daenx.test.service.impl;

import cn.daenx.common.constant.SystemConstant;
import cn.daenx.common.utils.MyUtil;
import cn.daenx.framework.dataScope.annotation.DataScope;
import cn.daenx.common.exception.MyException;
import cn.daenx.common.vo.ComStatusUpdVo;
import cn.daenx.test.domain.vo.TestDataTreeAddVo;
import cn.daenx.test.domain.vo.TestDataTreePageVo;
import cn.daenx.test.domain.vo.TestDataTreeUpdVo;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.daenx.test.mapper.TestDataTreeMapper;
import cn.daenx.test.domain.po.TestDataTree;
import cn.daenx.test.service.TestDataTreeService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestDataTreeServiceImpl extends ServiceImpl<TestDataTreeMapper, TestDataTree> implements TestDataTreeService {
    @Resource
    private TestDataTreeMapper testDataTreeMapper;

    private LambdaQueryWrapper<TestDataTree> getWrapper(TestDataTreePageVo vo) {
        LambdaQueryWrapper<TestDataTree> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getTitle()), TestDataTree::getTitle, vo.getTitle());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getType()), TestDataTree::getType, vo.getType());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getStatus()), TestDataTree::getStatus, vo.getStatus());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getContent()), TestDataTree::getContent, vo.getContent());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getRemark()), TestDataTree::getRemark, vo.getRemark());
        String startTime = vo.getStartTime();
        String endTime = vo.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), TestDataTree::getCreateTime, startTime, endTime);
        return wrapper;
    }

    /**
     * 获取列表
     *
     * @param vo
     * @return
     */
    @DataScope(alias = "test_data_tree")
    @Override
    public List<TestDataTree> getAll(TestDataTreePageVo vo) {
        LambdaQueryWrapper<TestDataTree> wrapper = getWrapper(vo);
        List<TestDataTree> list = testDataTreeMapper.selectList(wrapper);
        return list;
    }

    /**
     * 新增
     *
     * @param vo
     */
    @Override
    public void addInfo(TestDataTreeAddVo vo) {
        TestDataTree testDataTree = new TestDataTree();
        testDataTree.setParentId(vo.getParentId());
        testDataTree.setTitle(vo.getTitle());
        testDataTree.setContent(vo.getContent());
        testDataTree.setType(vo.getType());
        testDataTree.setStatus(vo.getStatus());
        testDataTree.setRemark(vo.getRemark());
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
     * @param vo
     */
    @DataScope(alias = "test_data_tree")
    @Override
    public void editInfo(TestDataTreeUpdVo vo) {
        LambdaUpdateWrapper<TestDataTree> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(TestDataTree::getId, vo.getId());
        updateWrapper.set(TestDataTree::getParentId, vo.getParentId());
        updateWrapper.set(TestDataTree::getTitle, vo.getTitle());
        updateWrapper.set(TestDataTree::getContent, vo.getContent());
        updateWrapper.set(TestDataTree::getType, vo.getType());
        updateWrapper.set(TestDataTree::getStatus, vo.getStatus());
        updateWrapper.set(TestDataTree::getRemark, vo.getRemark());
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
     * @param vo
     */
    @DataScope(alias = "test_data_tree")
    @Override
    public void changeStatus(ComStatusUpdVo vo) {
        LambdaUpdateWrapper<TestDataTree> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(TestDataTree::getId, vo.getId());
        updateWrapper.set(TestDataTree::getStatus, vo.getStatus());
        int rows = testDataTreeMapper.update(new TestDataTree(), updateWrapper);
        if (rows < 1) {
            throw new MyException("修改失败");
        }
        if (vo.getStatus().equals(SystemConstant.STATUS_DISABLE)) {
            //禁用所有子级
            List<TestDataTree> list = testDataTreeMapper.selectList(new LambdaQueryWrapper<>());
            List<TestDataTree> retList = handleListByParentId(list, vo.getId());
            List<String> idList = MyUtil.joinToList(retList, TestDataTree::getId);
            LambdaUpdateWrapper<TestDataTree> updateWrapper2 = new LambdaUpdateWrapper<>();
            updateWrapper2.in(TestDataTree::getId, idList);
            updateWrapper2.set(TestDataTree::getStatus, SystemConstant.STATUS_DISABLE);
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
