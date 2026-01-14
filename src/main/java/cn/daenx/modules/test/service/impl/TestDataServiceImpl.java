package cn.daenx.modules.test.service.impl;

import cn.daenx.framework.common.constant.SystemConstant;
import cn.daenx.framework.common.domain.dto.ComStatusUpdDto;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.dataScope.annotation.DataScope;
import cn.daenx.modules.test.domain.dto.testData.TestDataAddDto;
import cn.daenx.modules.test.domain.dto.testData.TestDataImportDto;
import cn.daenx.modules.test.domain.dto.testData.TestDataPageDto;
import cn.daenx.modules.test.domain.dto.testData.TestDataUpdDto;
import cn.daenx.modules.test.domain.po.TestData;
import cn.daenx.modules.test.domain.vo.testData.TestDataPageVo;
import cn.daenx.modules.test.mapper.TestDataMapper;
import cn.daenx.modules.test.service.TestDataService;
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

    private LambdaQueryWrapper<TestData> getWrapper(TestDataPageDto dto) {
        LambdaQueryWrapper<TestData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getTitle()), TestData::getTitle, dto.getTitle());
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getType()), TestData::getType, dto.getType());
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getStatus()), TestData::getStatus, dto.getStatus());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getContent()), TestData::getContent, dto.getContent());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getRemark()), TestData::getRemark, dto.getRemark());
        String startTime = dto.getStartTime();
        String endTime = dto.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), TestData::getCreateTime, startTime, endTime);
        return wrapper;
    }

    private QueryWrapper<TestData> getWrapperQuery(TestDataPageDto dto) {
        QueryWrapper<TestData> wrapper = new QueryWrapper<>();
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getTitle()), "td.title", dto.getTitle());
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getType()), "td.type", dto.getType());
        wrapper.eq(ObjectUtil.isNotEmpty(dto.getStatus()), "td.status", dto.getStatus());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getContent()), "td.content", dto.getContent());
        wrapper.like(ObjectUtil.isNotEmpty(dto.getRemark()), "td.remark", dto.getRemark());
        String startTime = dto.getStartTime();
        String endTime = dto.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), "td.create_time", startTime, endTime);
        wrapper.eq("td.is_delete", SystemConstant.IS_DELETE_NO);
        return wrapper;
    }

    /**
     * 分页列表
     * 测试数据分页列表_MP分页插件
     *
     * @param dto
     * @return
     */
    @DataScope(alias = "test_data")
    @Override
    public IPage<TestData> getPage(TestDataPageDto dto) {
        LambdaQueryWrapper<TestData> wrapper = getWrapper(dto);
        Page<TestData> testDataPage = testDataMapper.selectPage(dto.getPage(true), wrapper);
        return testDataPage;
    }

    /**
     * 分页列表2
     * 测试数据分页列表_自己写的SQL
     *
     * @param dto
     * @return
     */
    @Override
    public IPage<TestDataPageVo> getPage2(TestDataPageDto dto) {
        Page<TestDataPageVo> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        IPage<TestDataPageVo> iPage = testDataMapper.getPage(page, dto);
        return iPage;
    }

    /**
     * 分页列表3
     * 测试数据分页列表_MP自定义SQL
     *
     * @param dto
     * @return
     */
    @Override
    public IPage<TestDataPageVo> getPage3(TestDataPageDto dto) {
        QueryWrapper<TestData> wrapperQuery = getWrapperQuery(dto);
        IPage<TestDataPageVo> iPage = testDataMapper.getPageWrapper(dto.getPage(true), wrapperQuery);
        return iPage;
    }


    /**
     * 新增
     *
     * @param dto
     */
    @Override
    public void addInfo(TestDataAddDto dto) {
        TestData testData = new TestData();
        testData.setTitle(dto.getTitle());
        testData.setContent(dto.getContent());
        testData.setType(dto.getType());
        testData.setStatus(dto.getStatus());
        testData.setRemark(dto.getRemark());
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
     * @param dto
     */
    @DataScope(alias = "test_data")
    @Override
    public void editInfo(TestDataUpdDto dto) {
        LambdaUpdateWrapper<TestData> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(TestData::getId, dto.getId());
        updateWrapper.set(TestData::getTitle, dto.getTitle());
        updateWrapper.set(TestData::getContent, dto.getContent());
        updateWrapper.set(TestData::getType, dto.getType());
        updateWrapper.set(TestData::getStatus, dto.getStatus());
        updateWrapper.set(TestData::getRemark, dto.getRemark());
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
     * @param dto
     * @return
     */
    @Override
    public List<TestDataPageVo> exportData(TestDataPageDto dto) {
        QueryWrapper<TestData> wrapperQuery = getWrapperQuery(dto);
        List<TestDataPageVo> list = testDataMapper.getAll(wrapperQuery);
        return list;
    }

    /**
     * 导入
     *
     * @param dataList
     * @return
     */
    @Override
    public Integer importData(List<TestDataImportDto> dataList) {
        List<TestData> newList = new ArrayList<>();
        for (TestDataImportDto testDataVo : dataList) {
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
     * @param dto
     */
    @DataScope(alias = "test_data")
    @Override
    public void changeStatus(ComStatusUpdDto dto) {
        LambdaUpdateWrapper<TestData> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(TestData::getId, dto.getId());
        updateWrapper.set(TestData::getStatus, dto.getStatus());
        int rows = testDataMapper.update(new TestData(), updateWrapper);
        if (rows < 1) {
            throw new MyException("修改失败");
        }
    }
}
