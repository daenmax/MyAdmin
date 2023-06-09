package cn.daenx.myadmin.test.mapper;

import cn.daenx.myadmin.test.domain.po.TestDataTree;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TestDataTreeMapper extends BaseMapper<TestDataTree> {
    /**
     * 根据父级ID更新子级状态
     *
     * @param parentId
     * @param status
     * @return
     */
    Integer updateByParentId(@Param("parentId") String parentId, @Param("status") String status);
}
