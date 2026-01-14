package cn.daenx.modules.test.service;


import cn.daenx.framework.common.domain.dto.ComStatusUpdDto;
import cn.daenx.modules.test.domain.dto.testData.TestDataTreeAddDto;
import cn.daenx.modules.test.domain.dto.testData.TestDataTreePageDto;
import cn.daenx.modules.test.domain.dto.testData.TestDataTreeUpdDto;
import cn.daenx.modules.test.domain.po.TestDataTree;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface TestDataTreeService extends IService<TestDataTree> {

    /**
     * 获取列表
     *
     * @param dto
     * @return
     */
    List<TestDataTree> getAll(TestDataTreePageDto dto);

    /**
     * 新增
     *
     * @param dto
     */
    void addInfo(TestDataTreeAddDto dto);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    TestDataTree getInfo(String id);

    /**
     * 修改
     *
     * @param dto
     */
    void editInfo(TestDataTreeUpdDto dto);

    /**
     * 删除
     *
     * @param id
     */
    void deleteById(String id);

    /**
     * 修改状态
     *
     * @param dto
     */
    void changeStatus(ComStatusUpdDto dto);
}
