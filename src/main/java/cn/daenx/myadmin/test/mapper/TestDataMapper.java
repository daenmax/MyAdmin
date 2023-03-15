package cn.daenx.myadmin.test.mapper;

import cn.daenx.myadmin.common.annotation.DataScope;
import cn.daenx.myadmin.test.dto.TestDataPageDto;
import cn.daenx.myadmin.test.po.TestData;
import cn.daenx.myadmin.test.vo.TestDataPageVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TestDataMapper extends BaseMapper<TestData> {
    /**
     * 测试数据分页列表
     *
     * @param page
     * @param vo
     * @return
     */
    @DataScope(alias = "td")
    IPage<TestDataPageDto> getPage(Page<TestDataPageDto> page, @Param("vo") TestDataPageVo vo);

    /**
     * 测试数据分页列表
     *
     * @param page
     * @param wrapper
     * @return
     */
    @DataScope(alias = "td")
    IPage<TestDataPageDto> getPageWrapper(Page<TestDataPageDto> page, @Param("ew") Wrapper<TestData> wrapper);

}
