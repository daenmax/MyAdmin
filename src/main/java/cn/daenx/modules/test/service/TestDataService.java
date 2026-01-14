package cn.daenx.modules.test.service;


import cn.daenx.framework.common.domain.dto.ComStatusUpdDto;
import cn.daenx.modules.test.domain.dto.testData.TestDataAddDto;
import cn.daenx.modules.test.domain.dto.testData.TestDataImportDto;
import cn.daenx.modules.test.domain.dto.testData.TestDataPageDto;
import cn.daenx.modules.test.domain.dto.testData.TestDataUpdDto;
import cn.daenx.modules.test.domain.po.TestData;
import cn.daenx.modules.test.domain.vo.testData.TestDataPageVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface TestDataService extends IService<TestData> {

    /**
     * 分页列表
     * 测试数据分页列表_MP分页插件
     *
     * @param dto
     * @return
     */
    IPage<TestData> getPage(TestDataPageDto dto);

    /**
     * 分页列表2
     * 测试数据分页列表_自己写的SQL
     *
     * @param dto
     * @return
     */
    IPage<TestDataPageVo> getPage2(TestDataPageDto dto);

    /**
     * 分页列表3
     * 测试数据分页列表_MP自定义SQL
     *
     * @param dto
     * @return
     */
    IPage<TestDataPageVo> getPage3(TestDataPageDto dto);

    /**
     * 新增
     *
     * @param dto
     */
    void addInfo(TestDataAddDto dto);

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
     * @param dto
     */
    void editInfo(TestDataUpdDto dto);

    /**
     * 删除
     *
     * @param ids
     */
    void deleteByIds(List<String> ids);

    /**
     * 导出
     *
     * @param dto
     * @return
     */
    List<TestDataPageVo> exportData(TestDataPageDto dto);

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
     * @param dto
     */
    void changeStatus(ComStatusUpdDto dto);

}
