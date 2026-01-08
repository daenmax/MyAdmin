package cn.daenx.modules.test.mapper;

import cn.daenx.modules.test.domain.dto.testData.TestDataPageVo;
import cn.daenx.modules.test.domain.vo.testData.TestDataPageDto;
import cn.daenx.framework.dataScope.annotation.DataScope;
import cn.daenx.modules.test.domain.po.TestData;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface TestDataMapper extends BaseMapper<TestData> {
    /**
     * 测试数据分页列表_自己写的SQL
     *
     * @param page
     * @param vo
     * @return
     */
    @DataScope(alias = "td")
    IPage<TestDataPageVo> getPage(Page<TestDataPageVo> page, @Param("vo") TestDataPageDto vo);

    /**
     * 测试数据分页列表_MP自定义SQL
     *
     * @param page
     * @param wrapper
     * @return
     */
    @DataScope(alias = "td")
    IPage<TestDataPageVo> getPageWrapper(Page<TestDataPageVo> page, @Param("ew") Wrapper<TestData> wrapper);

    /**
     * 获取所有列表，用于导出
     *
     * @param wrapper
     * @return
     */
    @DataScope(alias = "td")
    List<TestDataPageVo> getAll(@Param("ew") Wrapper<TestData> wrapper);

}
