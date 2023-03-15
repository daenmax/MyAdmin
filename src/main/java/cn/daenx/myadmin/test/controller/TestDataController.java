package cn.daenx.myadmin.test.controller;

import cn.daenx.myadmin.common.excel.ExcelResult;
import cn.daenx.myadmin.common.utils.MyUtil;
import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.system.po.SysDict;
import cn.daenx.myadmin.system.vo.SysDictAddVo;
import cn.daenx.myadmin.system.vo.SysDictPageVo;
import cn.daenx.myadmin.system.vo.SysDictUpdVo;
import cn.daenx.myadmin.test.dto.TestDataPageDto;
import cn.daenx.myadmin.test.po.TestData;
import cn.daenx.myadmin.test.service.TestDataService;
import cn.daenx.myadmin.test.vo.TestDataImportVo;
import cn.daenx.myadmin.test.vo.TestDataPageVo;
import cn.daenx.myadmin.test.vo.TestDataUpdVo;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.bean.BeanUtil;
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
     * 测试数据分页列表_MP分页插件
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("test:data:list")
    @GetMapping("/list1")
    public Result list1(TestDataPageVo vo) {
        IPage<TestData> page = testDataService.getPage1(vo);
        return Result.ok(page);
    }

    /**
     * 测试数据分页列表_自己写的SQL
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("test:data:list")
    @GetMapping("/list2")
    public Result list2(TestDataPageVo vo) {
        IPage<TestDataPageDto> page = testDataService.getPage2(vo);
        return Result.ok(page);
    }

    /**
     * 测试数据分页列表_MP自定义SQL
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("test:data:list")
    @GetMapping("/list3")
    public Result list3(TestDataPageVo vo) {
        IPage<TestDataPageDto> page = testDataService.getPage3(vo);
        return Result.ok(page);
    }

    /**
     * 新增
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("test:data:add")
    @PostMapping
    public Result add(@Validated @RequestBody cn.daenx.myadmin.test.vo.TestDataAddVo vo) {
        testDataService.addData(vo);
        return Result.ok();
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @SaCheckPermission("test:data:query")
    @GetMapping(value = "/{id}")
    public Result query(@PathVariable String id) {
        TestData testData = testDataService.getData(id);
        return Result.ok(testData);
    }

    /**
     * 修改
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("test:data:edit")
    @PutMapping
    public Result edit(@Validated @RequestBody TestDataUpdVo vo) {
        testDataService.editData(vo);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @SaCheckPermission("test:data:remove")
    @DeleteMapping("/{ids}")
    public Result remove(@PathVariable String[] ids) {
        testDataService.deleteByIds(ids);
        return Result.ok();
    }


    /**
     * 导出
     */
    @SaCheckPermission("test:data:export")
    @PostMapping("/export")
    public void export(TestDataPageVo vo, HttpServletResponse response) {
        List<TestDataPageDto> list = testDataService.getAll(vo);
        MyUtil.exportXlsx(response, "测试数据", "测试数据", list, TestDataPageDto.class);
    }


    /**
     * 导入
     */
    @SaCheckPermission("test:data:import")
    @PostMapping("/importData")
    public Result importData(@RequestPart("file") MultipartFile file) throws IOException {
        ExcelResult<TestDataImportVo> excelResult = MyUtil.importExcel(file.getInputStream(), TestDataImportVo.class, true);
        List<TestDataImportVo> dataList = excelResult.getList();
        Integer num = testDataService.importData(dataList);
        return Result.ok("成功导入" + num + "条");
    }

    /**
     * 下载导入模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        MyUtil.exportXlsx(response, "测试数据", "测试数据", new ArrayList<>(), TestDataImportVo.class);
    }
}
