package cn.daenx.data.test.mapper;

import cn.daenx.framework.dataScope.annotation.DataScope;
import cn.daenx.data.test.domain.dto.TestDataPageDto;
import cn.daenx.data.test.domain.po.TestData;
import cn.daenx.data.test.domain.vo.TestDataPageVo;
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
    IPage<TestDataPageDto> getPage(Page<TestDataPageDto> page, @Param("vo") TestDataPageVo vo);

    /**
     * 测试数据分页列表_MP自定义SQL
     *
     * @param page
     * @param wrapper
     * @return
     */
    @DataScope(alias = "td")
    IPage<TestDataPageDto> getPageWrapper(Page<TestDataPageDto> page, @Param("ew") Wrapper<TestData> wrapper);

    /**
     * 获取所有列表，用于导出
     *
     * @param wrapper
     * @return
     */
    @DataScope(alias = "td")
    List<TestDataPageDto> getAll(@Param("ew") Wrapper<TestData> wrapper);

}
