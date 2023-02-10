package cn.daenx.myadmin.test.service.impl;

import cn.daenx.myadmin.test.dto.TestDataPageDto;
import cn.daenx.myadmin.test.mapper.TestDataMapper;
import cn.daenx.myadmin.test.po.TestData;
import cn.daenx.myadmin.test.service.TestDataService;
import cn.daenx.myadmin.test.vo.TestDataPageVo;
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
     * 测试数据分页列表
     *
     * @param vo
     * @return
     */
    @Override
    public IPage<TestDataPageDto> getPage(TestDataPageVo vo) {
        Page<TestDataPageDto> page = new Page<>(vo.getPageNum(), vo.getPageSize());
        IPage<TestDataPageDto> iPage = testDataMapper.getPage(page, vo);
        return iPage;
    }
}
