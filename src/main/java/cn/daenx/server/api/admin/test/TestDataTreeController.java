package cn.daenx.server.api.admin.test;

import cn.daenx.framework.common.constant.enums.LogOperType;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.vo.ComStatusUpdVo;
import cn.daenx.framework.common.vo.Result;
import cn.daenx.framework.logSave.annotation.Log;
import cn.daenx.test.domain.po.TestDataTree;
import cn.daenx.test.domain.vo.TestDataTreeAddVo;
import cn.daenx.test.domain.vo.TestDataTreePageVo;
import cn.daenx.test.domain.vo.TestDataTreeUpdVo;
import cn.daenx.test.service.TestDataTreeService;
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
    @Log(name = "测试树表数据-列表", type = LogOperType.QUERY, recordParams = true, recordResult = true)
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
    @Log(name = "测试树表数据-新增", type = LogOperType.ADD, recordParams = true, recordResult = true)
    @SaCheckPermission("test:dataTree:add")
    @PostMapping("/add")
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
    @Log(name = "测试树表数据-查询", type = LogOperType.QUERY, recordParams = true, recordResult = true)
    @SaCheckPermission("test:dataTree:query")
    @GetMapping(value = "/query")
    public Result query(@RequestParam(name = "id", required = true) String id) {
        TestDataTree testDataTree = testDataTreeService.getInfo(id);
        return Result.ok(testDataTree);
    }

    /**
     * 测试树表数据-修改
     *
     * @param vo
     * @return
     */
    @Log(name = "测试树表数据-修改", type = LogOperType.EDIT, recordParams = true, recordResult = true)
    @SaCheckPermission("test:dataTree:edit")
    @PostMapping("/edit")
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
    @Log(name = "测试树表数据-删除", type = LogOperType.REMOVE, recordParams = true, recordResult = true)
    @SaCheckPermission("test:dataTree:del")
    @PostMapping("/del")
    public Result del(@RequestParam(value = "id") String id) {
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
    @PostMapping("/changeStatus")
    public Result changeStatus(@Validated @RequestBody ComStatusUpdVo vo) {
        testDataTreeService.changeStatus(vo);
        return Result.ok();
    }
}
