package cn.daenx.admin.controller.system;

import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.vo.Result;
import cn.daenx.system.domain.dto.SysUserPageDto;
import cn.daenx.system.domain.po.SysDept;
import cn.daenx.system.domain.vo.SysDeptAddVo;
import cn.daenx.system.domain.vo.SysDeptPageVo;
import cn.daenx.system.domain.vo.SysDeptUpdVo;
import cn.daenx.system.service.SysDeptService;
import cn.daenx.system.service.SysUserService;
import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/system/dept")
public class SysDeptController {
    @Resource
    private SysDeptService sysDeptService;
    @Resource
    private SysUserService sysUserService;

    /**
     * 列表
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:dept:list")
    @GetMapping(value = "/list")
    public Result list(SysDeptPageVo vo) {
        List<SysDept> list = sysDeptService.getAll(vo);
        return Result.ok(list);
    }

    /**
     * 列表_排除自己
     *
     * @param id
     * @return
     */
    @SaCheckPermission("system:dept:list")
    @GetMapping(value = "/list/exclude/{id}")
    public Result excludeChild(@PathVariable(value = "id", required = false) String id) {
        List<SysDept> list = sysDeptService.getList(new SysDeptPageVo());
        List<SysDept> collect = list.stream().filter(item -> item.getId().equals(id)).collect(Collectors.toList());
        sysDeptService.removeList(list, collect);
        return Result.ok(list);
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @SaCheckPermission("system:dept:query")
    @GetMapping(value = "/{id}")
    public Result query(@PathVariable String id) {
        SysDept sysDept = sysDeptService.getInfo(id);
        return Result.ok(sysDept);
    }

    /**
     * 修改
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:dept:edit")
    @PostMapping("/edit")
    public Result edit(@Validated @RequestBody SysDeptUpdVo vo) {
        sysDeptService.editInfo(vo);
        return Result.ok();
    }

    /**
     * 新增
     *
     * @param vo
     * @return
     */
    @SaCheckPermission("system:dept:add")
    @PostMapping("/add")
    public Result add(@Validated @RequestBody SysDeptAddVo vo) {
        sysDeptService.addInfo(vo);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @SaCheckPermission("system:dept:remove")
    @PostMapping("/remove/{id}")
    public Result remove(@PathVariable("id") String id) {
        if (StringUtils.isBlank(id)) {
            throw new MyException("参数错误");
        }
        sysDeptService.deleteById(id);
        return Result.ok();
    }

    /**
     * 获取用户列表
     *
     * @param id        如果传ID，则会忽略其他全部参数
     * @param keyword
     * @return
     */
    @SaCheckPermission("system:user:list")
    @GetMapping(value = "/userList")
    public Result getUserList(String id, String keyword) {
        List<SysUserPageDto> userList = sysUserService.getUserList(id, keyword, keyword, keyword, keyword, keyword);
        return Result.ok(userList);
    }
}
