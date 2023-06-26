package cn.daenx.myadmin.test.controller;

import cn.daenx.myadmin.framework.logSave.annotation.Log;
import cn.daenx.myadmin.common.constant.enums.LogOperType;
import cn.daenx.myadmin.framework.excel.ExcelResult;
import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.utils.ExcelUtil;
import cn.daenx.myadmin.common.vo.ComStatusUpdVo;
import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.test.domain.dto.TestDataPageDto;
import cn.daenx.myadmin.test.domain.po.TestData;
import cn.daenx.myadmin.test.domain.vo.TestDataAddVo;
import cn.daenx.myadmin.test.service.TestDataService;
import cn.daenx.myadmin.test.domain.vo.TestDataImportVo;
import cn.daenx.myadmin.test.domain.vo.TestDataPageVo;
import cn.daenx.myadmin.test.domain.vo.TestDataUpdVo;
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

@RestController
@RequestMapping("/test/data")
public class TestDataController {
    @Resource
    private TestDataService testDataService;

    /**
     * 测试数据-分页列表_MP分页插件
     *
     * @param vo
     * @return
     */
    @Log(name = "测试数据", type = LogOperType.QUERY, recordParams = true, recordResult = true)
    @SaCheckPermission("test:data:list")
    @GetMapping("/list1")
    public Result list1(TestDataPageVo vo) {
        IPage<TestData> page = testDataService.getPage1(vo);
        return Result.ok(page);
    }

    /**
     * 测试数据-分页列表_自己写的SQL
     *
     * @param vo
     * @return
     */
    @Log(name = "测试数据", type = LogOperType.QUERY, recordParams = true, recordResult = true)
    @SaCheckPermission("test:data:list")
    @GetMapping("/list2")
    public Result list2(TestDataPageVo vo) {
        IPage<TestDataPageDto> page = testDataService.getPage2(vo);
        return Result.ok(page);
    }

    /**
     * 测试数据-分页列表_MP自定义SQL
     *
     * @param vo
     * @return
     */
    @Log(name = "测试数据", type = LogOperType.QUERY, recordParams = true, recordResult = true)
    @SaCheckPermission("test:data:list")
    @GetMapping("/list3")
    public Result list3(TestDataPageVo vo) {
        IPage<TestDataPageDto> page = testDataService.getPage3(vo);
        return Result.ok(page);
    }

    /**
     * 测试数据-新增
     *
     * @param vo
     * @return
     */
    @Log(name = "测试数据", type = LogOperType.ADD, recordParams = true, recordResult = true)
    @SaCheckPermission("test:data:add")
    @PostMapping("/add")
    public Result add(@Validated @RequestBody TestDataAddVo vo) {
        testDataService.addInfo(vo);
        return Result.ok();
    }

    /**
     * 测试数据-查询
     *
     * @param id
     * @return
     */
    @Log(name = "测试数据", type = LogOperType.QUERY, recordParams = true, recordResult = true)
    @SaCheckPermission("test:data:query")
    @GetMapping(value = "/{id}")
    public Result query(@PathVariable String id) {
        TestData testData = testDataService.getInfo(id);
        return Result.ok(testData);
    }

    /**
     * 测试数据-修改
     *
     * @param vo
     * @return
     */
    @Log(name = "测试数据", type = LogOperType.EDIT, recordParams = true, recordResult = true)
    @SaCheckPermission("test:data:edit")
    @PostMapping("/edit")
    public Result edit(@Validated @RequestBody TestDataUpdVo vo) {
        testDataService.editInfo(vo);
        return Result.ok();
    }

    /**
     * 测试数据-删除
     *
     * @param ids
     * @return
     */
    @Log(name = "测试数据", type = LogOperType.REMOVE, recordParams = true, recordResult = true)
    @SaCheckPermission("test:data:remove")
    @PostMapping("/remove")
    public Result remove(@RequestBody List<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            throw new MyException("参数错误");
        }
        testDataService.deleteByIds(ids);
        return Result.ok();
    }

    /**
     * 测试数据-导出
     */
    @Log(name = "测试数据", type = LogOperType.EXPORT, recordParams = true, recordResult = false)
    @SaCheckPermission("test:data:export")
    @PostMapping("/export")
    public void export(TestDataPageVo vo, HttpServletResponse response) {
        List<TestDataPageDto> list = testDataService.getAll(vo);
        ExcelUtil.exportXlsx(response, "测试数据", "测试数据", list, TestDataPageDto.class);
    }

    /**
     * 测试数据-导入
     */
    @Log(name = "测试数据", type = LogOperType.IMPORT, recordParams = false, recordResult = true)
    @SaCheckPermission("test:data:import")
    @PostMapping("/importData")
    public Result importData(@RequestPart("file") MultipartFile file) throws IOException {
        ExcelResult<TestDataImportVo> excelResult = ExcelUtil.importExcel(file.getInputStream(), TestDataImportVo.class, true);
        List<TestDataImportVo> dataList = excelResult.getList();
        Integer num = testDataService.importInfo(dataList);
        return Result.ok("成功导入" + num + "条");
    }

    /**
     * 测试数据-下载导入模板
     */
    @Log(name = "测试数据-下载导入模板", type = LogOperType.OTHER, recordParams = true, recordResult = false)
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil.exportXlsx(response, "测试数据", "测试数据", new ArrayList<>(), TestDataImportVo.class);
    }

    /**
     * 测试数据-修改状态
     *
     * @param vo
     * @return
     */
    @Log(name = "测试数据-修改状态", type = LogOperType.EDIT, recordParams = true, recordResult = false)
    @SaCheckPermission("test:data:edit")
    @PostMapping("/changeStatus")
    public Result changeStatus(@Validated @RequestBody ComStatusUpdVo vo) {
        testDataService.changeStatus(vo);
        return Result.ok();
    }
}
