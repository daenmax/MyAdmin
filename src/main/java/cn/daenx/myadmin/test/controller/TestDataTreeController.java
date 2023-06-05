package cn.daenx.myadmin.test.controller;

import cn.daenx.myadmin.common.annotation.Log;
import cn.daenx.myadmin.common.constant.enums.LogOperType;
import cn.daenx.myadmin.common.exception.MyException;
import cn.daenx.myadmin.common.vo.ComStatusUpdVo;
import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.test.po.TestDataTree;
import cn.daenx.myadmin.test.service.TestDataTreeService;
import cn.daenx.myadmin.test.vo.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test/dataTree")
public class TestDataTreeController {
    @Resource
    private TestDataTreeService testDataTreeService;

    /**
     * 测试树表数据-列表
     *
     * @param vo
     * @return
     */
    @Log(name = "测试树表数据", type = LogOperType.QUERY, recordParams = true, recordResult = true)
    @SaCheckPermission("test:dataTree:list")
    @GetMapping("/list")
    public Result list(TestDataTreePageVo vo) {
        List<TestDataTree> list = testDataTreeService.getAll(vo);
        return Result.ok(list);
    }

    /**
     * 测试树表数据-新增
     *
     * @param vo
     * @return
     */
    @Log(name = "测试树表数据", type = LogOperType.ADD, recordParams = true, recordResult = true)
    @SaCheckPermission("test:dataTree:add")
    @PostMapping
    public Result add(@Validated @RequestBody TestDataTreeAddVo vo) {
        testDataTreeService.addInfo(vo);
        return Result.ok();
    }

    /**
     * 测试树表数据-查询
     *
     * @param id
     * @return
     */
    @Log(name = "测试树表数据", type = LogOperType.QUERY, recordParams = true, recordResult = true)
    @SaCheckPermission("test:dataTree:query")
    @GetMapping(value = "/{id}")
    public Result query(@PathVariable String id) {
        TestDataTree testDataTree = testDataTreeService.getInfo(id);
        return Result.ok(testDataTree);
    }

    /**
     * 测试树表数据-修改
     *
     * @param vo
     * @return
     */
    @Log(name = "测试树表数据", type = LogOperType.EDIT, recordParams = true, recordResult = true)
    @SaCheckPermission("test:dataTree:edit")
    @PutMapping
    public Result edit(@Validated @RequestBody TestDataTreeUpdVo vo) {
        testDataTreeService.editInfo(vo);
        return Result.ok();
    }

    /**
     * 测试树表数据-删除
     *
     * @param id
     * @return
     */
    @Log(name = "测试树表数据", type = LogOperType.REMOVE, recordParams = true, recordResult = true)
    @SaCheckPermission("test:dataTree:remove")
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable("id") String id) {
        if (StringUtils.isBlank(id)) {
            throw new MyException("参数错误");
        }
        testDataTreeService.deleteById(id);
        return Result.ok();
    }

    /**
     * 测试数据-修改状态
     *
     * @param vo
     * @return
     */
    @Log(name = "测试数据-修改状态", type = LogOperType.EDIT, recordParams = true, recordResult = false)
    @SaCheckPermission("test:data:edit")
    @PutMapping("/changeStatus")
    public Result changeStatus(@Validated @RequestBody ComStatusUpdVo vo) {
        testDataTreeService.changeStatus(vo);
        return Result.ok();
    }
}
