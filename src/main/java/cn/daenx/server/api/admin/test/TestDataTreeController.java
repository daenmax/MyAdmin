package cn.daenx.server.api.admin.test;

import cn.daenx.framework.common.constant.enums.LogOperType;
import cn.daenx.framework.common.domain.dto.ComStatusUpdDto;
import cn.daenx.framework.common.domain.vo.Result;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.logSave.annotation.Log;
import cn.daenx.modules.test.domain.dto.testData.TestDataTreeAddDto;
import cn.daenx.modules.test.domain.dto.testData.TestDataTreePageDto;
import cn.daenx.modules.test.domain.dto.testData.TestDataTreeUpdDto;
import cn.daenx.modules.test.domain.po.TestDataTree;
import cn.daenx.modules.test.service.TestDataTreeService;
import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 测试树表数据
 */
@RestController
@RequestMapping("/test/dataTree")
public class TestDataTreeController {
    @Resource
    private TestDataTreeService testDataTreeService;

    /**
     * 测试树表数据-列表
     *
     * @param dto
     * @return
     */
    @Log(name = "测试树表数据-列表", type = LogOperType.QUERY, recordParams = true, recordResult = true)
    @SaCheckPermission("test:dataTree:list")
    @GetMapping("/list")
    public Result<List<TestDataTree>> list(TestDataTreePageDto dto) {
        List<TestDataTree> list = testDataTreeService.getAll(dto);
        return Result.ok(list);
    }

    /**
     * 测试树表数据-新增
     *
     * @param dto
     * @return
     */
    @Log(name = "测试树表数据-新增", type = LogOperType.ADD, recordParams = true, recordResult = true)
    @SaCheckPermission("test:dataTree:add")
    @PostMapping("/add")
    public Result<Void> add(@Validated @RequestBody TestDataTreeAddDto dto) {
        testDataTreeService.addInfo(dto);
        return Result.ok();
    }

    /**
     * 测试树表数据-查询
     *
     * @param id
     * @return
     */
    @Log(name = "测试树表数据-查询", type = LogOperType.QUERY, recordParams = true, recordResult = true)
    @SaCheckPermission("test:dataTree:query")
    @GetMapping(value = "/query")
    public Result<TestDataTree> query(@RequestParam(name = "id", required = true) String id) {
        TestDataTree testDataTree = testDataTreeService.getInfo(id);
        return Result.ok(testDataTree);
    }

    /**
     * 测试树表数据-修改
     *
     * @param dto
     * @return
     */
    @Log(name = "测试树表数据-修改", type = LogOperType.EDIT, recordParams = true, recordResult = true)
    @SaCheckPermission("test:dataTree:edit")
    @PostMapping(value = "/edit")
    public Result<Void> edit(@Validated @RequestBody TestDataTreeUpdDto dto) {
        testDataTreeService.editInfo(dto);
        return Result.ok();
    }

    /**
     * 测试树表数据-删除
     *
     * @param id
     * @return
     */
    @Log(name = "测试树表数据-删除", type = LogOperType.REMOVE, recordParams = true, recordResult = true)
    @SaCheckPermission("test:dataTree:del")
    @PostMapping(value = "/del")
    public Result<Void> del(@RequestParam(value = "id") String id) {
        if (StringUtils.isBlank(id)) {
            throw new MyException("参数错误");
        }
        testDataTreeService.deleteById(id);
        return Result.ok();
    }

    /**
     * 测试数据-修改状态
     *
     * @param dto
     * @return
     */
    @Log(name = "测试数据-修改状态", type = LogOperType.EDIT, recordParams = true, recordResult = false)
    @SaCheckPermission("test:data:edit")
    @PostMapping("/changeStatus")
    public Result<Void> changeStatus(@Validated @RequestBody ComStatusUpdDto dto) {
        testDataTreeService.changeStatus(dto);
        return Result.ok();
    }
}
