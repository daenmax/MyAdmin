package cn.daenx.data.system.mapper;

import cn.daenx.data.system.domain.po.SysPosition;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysPositionMapper extends BaseMapper<SysPosition> {
    List<SysPosition> getSysPositionListByUserId(@Param("userId") String userId);
}
