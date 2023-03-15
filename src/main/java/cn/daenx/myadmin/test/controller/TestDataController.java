package cn.daenx.myadmin.test.controller;

import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.test.dto.TestDataPageDto;
import cn.daenx.myadmin.test.po.TestData;
import cn.daenx.myadmin.test.service.TestDataService;
import cn.daenx.myadmin.test.vo.TestDataPageVo;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
