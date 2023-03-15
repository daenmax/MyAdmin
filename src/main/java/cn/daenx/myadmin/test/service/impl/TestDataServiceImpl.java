package cn.daenx.myadmin.test.service.impl;

import cn.daenx.myadmin.common.annotation.DataScope;
import cn.daenx.myadmin.system.po.SysDict;
import cn.daenx.myadmin.test.dto.TestDataPageDto;
import cn.daenx.myadmin.test.mapper.TestDataMapper;
import cn.daenx.myadmin.test.po.TestData;
import cn.daenx.myadmin.test.service.TestDataService;
import cn.daenx.myadmin.test.vo.TestDataPageVo;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TestDataServiceImpl extends ServiceImpl<TestDataMapper, TestData> implements TestDataService {
    @Resource
    private TestDataMapper testDataMapper;

    /**
     * 测试数据分页列表_MybatisPlus
     *
     * @param vo
     * @return
     */
    @DataScope(alias = "test_data")
    @Override
    public IPage<TestData> getPage1(TestDataPageVo vo) {
        LambdaQueryWrapper<TestData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getTitle()), TestData::getTitle, vo.getTitle());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getContent()), TestData::getContent, vo.getContent());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getRemark()), TestData::getRemark, vo.getRemark());
        String startTime = vo.getStartTime();
        String endTime = vo.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), TestData::getCreateTime, startTime, endTime);
        Page<TestData> testDataPage = testDataMapper.selectPage(vo.getPage(), wrapper);
        return testDataPage;
    }

    /**
     * 测试数据分页列表_自写SQL
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
     * 测试数据分页列表_半自写SQL
     *
     * @param vo
     * @return
     */
    @Override
    public IPage<TestDataPageDto> getPage3(TestDataPageVo vo) {
        QueryWrapper<TestData> wrapper = new QueryWrapper<>();
        wrapper.eq(ObjectUtil.isNotEmpty(vo.getTitle()), "td.title", vo.getTitle());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getContent()), "td.content", vo.getContent());
        wrapper.like(ObjectUtil.isNotEmpty(vo.getRemark()), "td.remark", vo.getRemark());
        String startTime = vo.getStartTime();
        String endTime = vo.getEndTime();
        wrapper.between(ObjectUtil.isNotEmpty(startTime) && ObjectUtil.isNotEmpty(endTime), "td.create_time", startTime, endTime);
        IPage<TestDataPageDto> iPage = testDataMapper.getPageWrapper(vo.getPage(), wrapper);
        return iPage;
    }
}
