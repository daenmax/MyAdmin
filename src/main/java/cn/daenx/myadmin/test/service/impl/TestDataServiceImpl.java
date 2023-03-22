package cn.daenx.myadmin.test.service.impl;

import cn.daenx.myadmin.common.annotation.DataScope;
import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.system.constant.SystemConstant;
import cn.daenx.myadmin.system.po.SysDict;
import cn.daenx.myadmin.test.dto.TestDataPageDto;
import cn.daenx.myadmin.test.mapper.TestDataMapper;
import cn.daenx.myadmin.test.po.TestData;
import cn.daenx.myadmin.test.service.TestDataService;
import cn.daenx.myadmin.test.vo.TestDataImportVo;
import cn.daenx.myadmin.test.vo.TestDataPageVo;
import cn.daenx.myadmin.test.vo.TestDataUpdVo;
import cn.daenx.myadmin.test.vo.TestDataAddVo;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TestDataServiceImpl extends ServiceImpl<TestDataMapper, TestData> implements TestDataService {
    @Resource
    private TestDataMapper testDataMapper;

    /**
     * 测试数据分页列表_MP分页插件
     *
     * @param vo
     * @return
     */
    @DataScope(alias = "test_data")
    @Override
    public IPage<TestData> getPage1(TestDataPageVo vo) {
        LambdaQueryWrapper<TestData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getTitle()), TestData::getTitle, vo.getTitle());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getType()), TestData::getType, vo.getType());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getContent()), TestData::getContent, vo.getContent());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getRemark()), TestData::getRemark, vo.getRemark());
        String startTime = vo.getStartTime();
        String endTime = vo.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), TestData::getCreateTime, startTime, endTime);
        Page<TestData> testDataPage = testDataMapper.selectPage(vo.getPage(true), wrapper);
        return testDataPage;
    }

    /**
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
     * 测试数据分页列表_MP自定义SQL
     *
     * @param vo
     * @return
     */
    @Override
    public IPage<TestDataPageDto> getPage3(TestDataPageVo vo) {
        QueryWrapper<TestData> wrapper = new QueryWrapper<>();
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getTitle()), "td.title", vo.getTitle());
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getType()), "td.type", vo.getType());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getContent()), "td.content", vo.getContent());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getRemark()), "td.remark", vo.getRemark());
        String startTime = vo.getStartTime();
        String endTime = vo.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), "td.create_time", startTime, endTime);
        wrapper.eq("td.is_delete", SystemConstant.IS_DELETE_NO);
        IPage<TestDataPageDto> iPage = testDataMapper.getPageWrapper(vo.getPage(true), wrapper);
        return iPage;
    }


    /**
     * 新增
     *
     * @param vo
     */
    @Override
    public void addData(TestDataAddVo vo) {
        TestData testData = new TestData();
        testData.setTitle(vo.getTitle());
        testData.setContent(vo.getContent());
        testData.setType(vo.getType());
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
    public TestData getData(String id) {
        return testDataMapper.selectById(id);
    }

    /**
     * 修改
     *
     * @param vo
     */
    @DataScope(alias = "test_data")
    @Override
    public void editData(TestDataUpdVo vo) {
        LambdaUpdateWrapper<TestData> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(TestData::getId, vo.getId());
        updateWrapper.set(TestData::getTitle, vo.getTitle());
        updateWrapper.set(TestData::getContent, vo.getContent());
        updateWrapper.set(TestData::getType, vo.getType());
        updateWrapper.set(TestData::getRemark, vo.getRemark());
        int rows = testDataMapper.update(null, updateWrapper);
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
    public void deleteByIds(String[] ids) {
        List<String> idList = Arrays.asList(ids);
        int i = testDataMapper.deleteBatchIds(idList);
        if (i < 1) {
            throw new MyException("删除失败");
        }
    }

    /**
     * 获取所有列表，用于导出
     *
     * @param vo
     * @return
     */
    @Override
    public List<TestDataPageDto> getAll(TestDataPageVo vo) {
        QueryWrapper<TestData> wrapper = new QueryWrapper<>();
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getTitle()), "td.title", vo.getTitle());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getContent()), "td.content", vo.getContent());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getRemark()), "td.remark", vo.getRemark());
        String customSqlSegment = wrapper.getCustomSqlSegment();
        System.out.println(customSqlSegment);
        String startTime = vo.getStartTime();
        String endTime = vo.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), "td.create_time", startTime, endTime);
        wrapper.eq("td.is_delete", SystemConstant.IS_DELETE_NO);
        List<TestDataPageDto> list = testDataMapper.getAll(wrapper);
        return list;
    }

    /**
     * 导入数据
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
}
