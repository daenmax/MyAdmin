package cn.daenx.modules.test.service;


import cn.daenx.framework.common.domain.dto.ComStatusUpdDto;
import cn.daenx.modules.test.domain.po.TestDataTree;
import cn.daenx.modules.test.domain.dto.testData.TestDataTreeAddDto;
import cn.daenx.modules.test.domain.dto.testData.TestDataTreePageDto;
import cn.daenx.modules.test.domain.dto.testData.TestDataTreeUpdDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface TestDataTreeService extends IService<TestDataTree> {

    /**
     * 获取列表
     *
     * @param vo
     * @return
     */
    List<TestDataTree> getAll(TestDataTreePageDto vo);

    /**
     * 新增
     *
     * @param vo
     */
    void addInfo(TestDataTreeAddDto vo);

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
     * @param vo
     */
    void editInfo(TestDataTreeUpdDto vo);

    /**
     * 删除
     *
     * @param id
     */
    void deleteById(String id);

    /**
     * 修改状态
     *
     * @param vo
     */
    void changeStatus(ComStatusUpdDto vo);
}
