package cn.daenx.myadmin.system.mapper;

import cn.daenx.myadmin.system.po.SysDict;
import cn.daenx.myadmin.system.vo.SysDictPageVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysDictMapper extends BaseMapper<SysDict> {
    /**
     * 分页列表
     *
     * @param page
     * @param vo
     * @return
     */
    IPage<SysDict> getPage(Page<SysDict> page, SysDictPageVo vo);
}
