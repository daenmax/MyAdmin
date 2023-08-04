package cn.daenx.system.service;

import cn.daenx.system.domain.dto.SysJobLogPageDto;
import cn.daenx.system.domain.po.SysJobLog;
import cn.daenx.system.domain.vo.SysJobLogPageVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysJobLogService extends IService<SysJobLog> {

    /**
     * 分页列表
     *
     * @param vo
     * @return
     */
    IPage<SysJobLogPageDto> getPage(SysJobLogPageVo vo);

    /**
     * 获取所有列表，用于导出
     *
     * @param vo
     * @return
     */
    List<SysJobLogPageDto> getAll(SysJobLogPageVo vo);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    SysJobLogPageDto getInfo(String id);

    /**
     * 删除
     * 未加数据权限
     *
     * @param ids
     */
    void deleteByIds(List<String> ids);

    /**
     * 清空日志
     * 未加数据权限
     */
    void clean();

}
