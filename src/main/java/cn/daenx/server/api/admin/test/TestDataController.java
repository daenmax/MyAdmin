package cn.daenx.server.api.admin.test;

import cn.daenx.framework.common.constant.enums.LogOperType;
import cn.daenx.framework.common.domain.dto.ComStatusUpdDto;
import cn.daenx.framework.common.domain.vo.Result;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.excel.ExcelResult;
import cn.daenx.framework.excel.utils.ExcelUtil;
import cn.daenx.framework.logSave.annotation.Log;
import cn.daenx.modules.test.domain.dto.testData.TestDataAddDto;
import cn.daenx.modules.test.domain.dto.testData.TestDataImportDto;
import cn.daenx.modules.test.domain.dto.testData.TestDataPageDto;
import cn.daenx.modules.test.domain.dto.testData.TestDataUpdDto;
import cn.daenx.modules.test.domain.po.TestData;
import cn.daenx.modules.test.domain.vo.testData.TestDataPageVo;
import cn.daenx.modules.test.service.TestDataService;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试数据
 */
@RestController
@RequestMapping("/test/data")
public class TestDataController {
    @Resource
    private TestDataService testDataService;

    /**
     * 分页列表
     * 测试数据-分页列表_MP分页插件
     *
     * @param dto
     * @return
     */
    @Log(name = "测试数据-分页列表_MP分页插件", type = LogOperType.QUERY, recordParams = true, recordResult = true)
    @SaCheckPermission("test:data:page")
    @GetMapping("/page")
    public Result<IPage<TestData>> page(TestDataPageDto dto) {
        IPage<TestData> page = testDataService.getPage(dto);
        return Result.ok(page);
    }

    /**
     * 分页列表2
     * 测试数据-分页列表_自己写的SQL
     *
     * @param dto
     * @return
     */
    @Log(name = "测试数据-分页列表_自己写的SQL", type = LogOperType.QUERY, recordParams = true, recordResult = true)
    @SaCheckPermission("test:data:page")
    @GetMapping("/page2")
    public Result<IPage<TestDataPageVo>> page2(TestDataPageDto dto) {
        IPage<TestDataPageVo> page = testDataService.getPage2(dto);
        return Result.ok(page);
    }

    /**
     * 分页列表3
     * 测试数据-分页列表_MP自定义SQL
     *
     * @param dto
     * @return
     */
    @Log(name = "测试数据-分页列表_MP自定义SQL", type = LogOperType.QUERY, recordParams = true, recordResult = true)
    @SaCheckPermission("test:data:page")
    @GetMapping("/page3")
    public Result<IPage<TestDataPageVo>> page3(TestDataPageDto dto) {
        IPage<TestDataPageVo> page = testDataService.getPage3(dto);
        return Result.ok(page);
    }

    /**
     * 新增
     *
     * @param dto
     * @return
     */
    @Log(name = "测试数据-新增", type = LogOperType.ADD, recordParams = true, recordResult = true)
    @SaCheckPermission("test:data:add")
    @PostMapping("/add")
    public Result<Void> add(@Validated @RequestBody TestDataAddDto dto) {
        testDataService.addInfo(dto);
        return Result.ok();
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @Log(name = "测试数据-查询", type = LogOperType.QUERY, recordParams = true, recordResult = true)
    @SaCheckPermission("test:data:query")
    @GetMapping(value = "/query")
    public Result<TestData> query(@RequestParam(name = "id", required = true) String id) {
        TestData testData = testDataService.getInfo(id);
        return Result.ok(testData);
    }

    /**
     * 修改
     *
     * @param dto
     * @return
     */
    @Log(name = "测试数据-修改", type = LogOperType.EDIT, recordParams = true, recordResult = true)
    @SaCheckPermission("test:data:edit")
    @GetMapping(value = "/edit")
    public Result<Void> edit(@Validated @RequestBody TestDataUpdDto dto) {
        testDataService.editInfo(dto);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @Log(name = "测试数据-删除", type = LogOperType.REMOVE, recordParams = true, recordResult = true)
    @SaCheckPermission("test:data:del")
    @PostMapping("/del")
    public Result<Void> remove(@RequestBody List<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            throw new MyException("参数错误");
        }
        testDataService.deleteByIds(ids);
        return Result.ok();
    }

    /**
     * 导出
     */
    @Log(name = "测试数据-导出", type = LogOperType.EXPORT, recordParams = true, recordResult = false)
    @SaCheckPermission("test:data:export")
    @PostMapping("/exportData")
    public void exportData(TestDataPageDto dto, HttpServletResponse response) {
        List<TestDataPageVo> list = testDataService.exportData(dto);
        ExcelUtil.exportXlsx(response, "测试数据", "测试数据", list, TestDataPageVo.class);
    }

    /**
     * 导入
     */
    @Log(name = "测试数据-导入", type = LogOperType.IMPORT, recordParams = false, recordResult = true)
    @SaCheckPermission("test:data:import")
    @PostMapping("/importData")
    public Result<String> importData(@RequestPart("file") MultipartFile file) throws IOException {
        ExcelResult<TestDataImportDto> excelResult = ExcelUtil.importExcel(file.getInputStream(), TestDataImportDto.class, true);
        List<TestDataImportDto> dataList = excelResult.getList();
        Integer num = testDataService.importData(dataList);
        return Result.ok("成功导入" + num + "条");
    }

    /**
     * 下载导入模板
     */
    @Log(name = "测试数据-下载导入模板", type = LogOperType.OTHER, recordParams = true, recordResult = false)
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil.exportXlsx(response, "测试数据", "测试数据", new ArrayList<>(), TestDataImportDto.class);
    }

    /**
     * 修改状态
     *
     * @param dto
     * @return
     */
    @Log(name = "测试数据-修改状态", type = LogOperType.EDIT, recordParams = true, recordResult = false)
    @SaCheckPermission("test:data:edit")
    @PostMapping("/changeStatus")
    public Result<Void> changeStatus(@Validated @RequestBody ComStatusUpdDto dto) {
        testDataService.changeStatus(dto);
        return Result.ok();
    }
}
