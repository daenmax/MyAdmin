package cn.daenx.test.service.impl;

import cn.daenx.framework.common.constant.SystemConstant;
import cn.daenx.framework.dataScope.annotation.DataScope;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.vo.ComStatusUpdVo;
import cn.daenx.test.domain.dto.TestDataPageDto;
import cn.daenx.test.mapper.TestDataMapper;
import cn.daenx.test.domain.po.TestData;
import cn.daenx.test.service.TestDataService;
import cn.daenx.test.domain.vo.TestDataImportVo;
import cn.daenx.test.domain.vo.TestDataPageVo;
import cn.daenx.test.domain.vo.TestDataUpdVo;
import cn.daenx.test.domain.vo.TestDataAddVo;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TestDataServiceImpl extends ServiceImpl<TestDataMapper, TestData> implements TestDataService {
    @Resource
    private TestDataMapper testDataMapper;

    private LambdaQueryWrapper<TestData> getWrapper(TestDataPageVo vo) {
        LambdaQueryWrapper<TestData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getTitle()), TestData::getTitle, vo.getTitle());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getType()), TestData::getType, vo.getType());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getStatus()), TestData::getStatus, vo.getStatus());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getContent()), TestData::getContent, vo.getContent());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getRemark()), TestData::getRemark, vo.getRemark());
        String startTime = vo.getStartTime();
        String endTime = vo.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), TestData::getCreateTime, startTime, endTime);
        return wrapper;
    }

    private QueryWrapper<TestData> getWrapperQuery(TestDataPageVo vo) {
        QueryWrapper<TestData> wrapper = new QueryWrapper<>();
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getTitle()), "td.title", vo.getTitle());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getType()), "td.type", vo.getType());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getStatus()), "td.status", vo.getStatus());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getContent()), "td.content", vo.getContent());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getRemark()), "td.remark", vo.getRemark());
        String startTime = vo.getStartTime();
        String endTime = vo.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), "td.create_time", startTime, endTime);
        wrapper.eq("td.is_delete", SystemConstant.IS_DELETE_NO);
        return wrapper;
    }

    /**
     * 分页列表
     * 测试数据分页列表_MP分页插件
     *
     * @param vo
     * @return
     */
    @DataScope(alias = "test_data")
    @Override
    public IPage<TestData> getPage(TestDataPageVo vo) {
        LambdaQueryWrapper<TestData> wrapper = getWrapper(vo);
        Page<TestData> testDataPage = testDataMapper.selectPage(vo.getPage(true), wrapper);
        return testDataPage;
    }

    /**
     * 分页列表2
     * 测试数据分页列表_自己写的SQL
     *
     * @param vo
     * @return
     */
    @Override
    public IPage<TestDataPageDto> getPage2(TestDataPageVo vo) {
        Page<TestDataPageDto> page = new Page<>(vo.getPageNum(), vo.getPageSize());
        IPage<TestDataPageDto> iPage = testDataMapper.getPage(page, vo);
        return iPage;
    }

    /**
     * 分页列表3
     * 测试数据分页列表_MP自定义SQL
     *
     * @param vo
     * @return
     */
    @Override
    public IPage<TestDataPageDto> getPage3(TestDataPageVo vo) {
        QueryWrapper<TestData> wrapperQuery = getWrapperQuery(vo);
        IPage<TestDataPageDto> iPage = testDataMapper.getPageWrapper(vo.getPage(true), wrapperQuery);
        return iPage;
    }


    /**
     * 新增
     *
     * @param vo
     */
    @Override
    public void addInfo(TestDataAddVo vo) {
        TestData testData = new TestData();
        testData.setTitle(vo.getTitle());
        testData.setContent(vo.getContent());
        testData.setType(vo.getType());
        testData.setStatus(vo.getStatus());
        testData.setRemark(vo.getRemark());
        int insert = testDataMapper.insert(testData);
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
    @DataScope(alias = "test_data")
    @Override
    public TestData getInfo(String id) {
        return testDataMapper.selectById(id);
    }

    /**
     * 修改
     *
     * @param vo
     */
    @DataScope(alias = "test_data")
    @Override
    public void editInfo(TestDataUpdVo vo) {
        LambdaUpdateWrapper<TestData> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(TestData::getId, vo.getId());
        updateWrapper.set(TestData::getTitle, vo.getTitle());
        updateWrapper.set(TestData::getContent, vo.getContent());
        updateWrapper.set(TestData::getType, vo.getType());
        updateWrapper.set(TestData::getStatus, vo.getStatus());
        updateWrapper.set(TestData::getRemark, vo.getRemark());
        int rows = testDataMapper.update(new TestData(), updateWrapper);
        if (rows < 1) {
            throw new MyException("修改失败");
        }
    }

    /**
     * 删除
     *
     * @param ids
     */
    @DataScope(alias = "test_data")
    @Override
    public void deleteByIds(List<String> ids) {
        int i = testDataMapper.deleteByIds(ids);
        if (i < 1) {
            throw new MyException("删除失败");
        }
    }

    /**
     * 导出
     *
     * @param vo
     * @return
     */
    @Override
    public List<TestDataPageDto> exportData(TestDataPageVo vo) {
        QueryWrapper<TestData> wrapperQuery = getWrapperQuery(vo);
        List<TestDataPageDto> list = testDataMapper.getAll(wrapperQuery);
        return list;
    }

    /**
     * 导入
     *
     * @param dataList
     * @return
     */
    @Override
    public Integer importData(List<TestDataImportVo> dataList) {
        List<TestData> newList = new ArrayList<>();
        for (TestDataImportVo testDataVo : dataList) {
            TestData testData = new TestData();
            testData.setTitle(testDataVo.getTitle());
            testData.setContent(testDataVo.getContent());
            testData.setType(testDataVo.getType());
            testData.setStatus(testDataVo.getStatus());
            testData.setRemark(testDataVo.getRemark());
            newList.add(testData);
        }
        try {
            saveBatch(newList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("导入失败：入库异常");
        }
        return dataList.size();
    }

    /**
     * 修改状态
     *
     * @param vo
     */
    @DataScope(alias = "test_data")
    @Override
    public void changeStatus(ComStatusUpdVo vo) {
        LambdaUpdateWrapper<TestData> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(TestData::getId, vo.getId());
        updateWrapper.set(TestData::getStatus, vo.getStatus());
        int rows = testDataMapper.update(new TestData(), updateWrapper);
        if (rows < 1) {
            throw new MyException("修改失败");
        }
    }
}
