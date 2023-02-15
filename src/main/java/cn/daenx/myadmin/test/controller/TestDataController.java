package cn.daenx.myadmin.test.controller;

import cn.daenx.myadmin.common.vo.Result;
import cn.daenx.myadmin.test.dto.TestDataPageDto;
import cn.daenx.myadmin.test.service.TestDataService;
import cn.daenx.myadmin.test.vo.TestDataPageVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testData")
public class TestDataController {
    @Resource
    private TestDataService testDataService;

    /**
     * 测试数据分页列表
     *
     * @param vo
     * @return
     */
    @GetMapping("/list")
    public Result list(TestDataPageVo vo) {
        IPage<TestDataPageDto> page = testDataService.getPage(vo);
        return Result.ok(page);
    }

}