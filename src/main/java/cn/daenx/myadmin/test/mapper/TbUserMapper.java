package cn.daenx.myadmin.test.mapper;

import cn.daenx.myadmin.test.po.TbUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbUserMapper extends BaseMapper<TbUser> {
}