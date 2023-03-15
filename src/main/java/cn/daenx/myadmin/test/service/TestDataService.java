package cn.daenx.myadmin.test.service;

import cn.daenx.myadmin.test.dto.TestDataPageDto;
import cn.daenx.myadmin.test.po.TestData;
import cn.daenx.myadmin.test.vo.TestDataPageVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface TestDataService extends IService<TestData> {

    /**
     * 测试数据分页列表_MybatisPlus
     *
     * @param vo
     * @return
     */
    IPage<TestData> getPage1(TestDataPageVo vo);

    /**
     * 测试数据分页列表_自写SQL
     *
     * @param vo
     * @return
     */
    IPage<TestDataPageDto> getPage2(TestDataPageVo vo);

    /**
     * 测试数据分页列表_半自写SQL
     *
     * @param vo
     * @return
     */
    IPage<TestDataPageDto> getPage3(TestDataPageVo vo);
}
