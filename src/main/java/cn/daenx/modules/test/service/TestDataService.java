package cn.daenx.modules.test.service;


import cn.daenx.modules.test.domain.dto.testData.TestDataPageVo;
import cn.daenx.modules.test.domain.vo.testData.TestDataPageDto;
import cn.daenx.framework.common.domain.dto.ComStatusUpdDto;
import cn.daenx.modules.test.domain.po.TestData;
import cn.daenx.modules.test.domain.vo.testData.TestDataAddDto;
import cn.daenx.modules.test.domain.vo.testData.TestDataImportDto;
import cn.daenx.modules.test.domain.vo.testData.TestDataUpdDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface TestDataService extends IService<TestData> {

    /**
     * 分页列表
     * 测试数据分页列表_MP分页插件
     *
     * @param vo
     * @return
     */
    IPage<TestData> getPage(TestDataPageDto vo);

    /**
     * 分页列表2
     * 测试数据分页列表_自己写的SQL
     *
     * @param vo
     * @return
     */
    IPage<TestDataPageVo> getPage2(TestDataPageDto vo);

    /**
     * 分页列表3
     * 测试数据分页列表_MP自定义SQL
     *
     * @param vo
     * @return
     */
    IPage<TestDataPageVo> getPage3(TestDataPageDto vo);

    /**
     * 新增
     *
     * @param vo
     */
    void addInfo(TestDataAddDto vo);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    TestData getInfo(String id);

    /**
     * 修改
     *
     * @param vo
     */
    void editInfo(TestDataUpdDto vo);

    /**
     * 删除
     *
     * @param ids
     */
    void deleteByIds(List<String> ids);

    /**
     * 导出
     *
     * @param vo
     * @return
     */
    List<TestDataPageVo> exportData(TestDataPageDto vo);

    /**
     * 导入
     *
     * @param dataList
     * @return
     */
    Integer importData(List<TestDataImportDto> dataList);

    /**
     * 修改状态
     *
     * @param vo
     */
    void changeStatus(ComStatusUpdDto vo);

}
