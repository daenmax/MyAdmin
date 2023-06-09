package cn.daenx.myadmin.test.service;

import cn.daenx.myadmin.common.vo.ComStatusUpdVo;
import cn.daenx.myadmin.test.domain.po.TestDataTree;
import cn.daenx.myadmin.test.domain.vo.TestDataTreeAddVo;
import cn.daenx.myadmin.test.domain.vo.TestDataTreePageVo;
import cn.daenx.myadmin.test.domain.vo.TestDataTreeUpdVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface TestDataTreeService extends IService<TestDataTree> {

    /**
     * 获取列表
     *
     * @param vo
     * @return
     */
    List<TestDataTree> getAll(TestDataTreePageVo vo);

    /**
     * 新增
     *
     * @param vo
     */
    void addInfo(TestDataTreeAddVo vo);

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
    void editInfo(TestDataTreeUpdVo vo);

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
    void changeStatus(ComStatusUpdVo vo);
}
