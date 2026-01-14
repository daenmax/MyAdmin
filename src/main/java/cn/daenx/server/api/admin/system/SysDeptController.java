package cn.daenx.server.api.admin.system;

import cn.daenx.framework.common.domain.vo.Result;
import cn.daenx.framework.common.exception.MyException;
import cn.daenx.modules.system.domain.dto.sysDept.SysDeptAddDto;
import cn.daenx.modules.system.domain.dto.sysDept.SysDeptPageDto;
import cn.daenx.modules.system.domain.dto.sysDept.SysDeptUpdDto;
import cn.daenx.modules.system.domain.po.SysDept;
import cn.daenx.modules.system.domain.vo.sysUser.SysUserPageVo;
import cn.daenx.modules.system.service.SysDeptService;
import cn.daenx.modules.system.service.SysUserService;
import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门管理
 */
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
     * @param dto
     * @return
     */
    @SaCheckPermission("system:dept:list")
    @GetMapping(value = "/list")
    public Result<List<SysDept>> list(SysDeptPageDto dto) {
        List<SysDept> list = sysDeptService.getAll(dto);
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
    public Result<List<SysDept>> excludeChild(@PathVariable(value = "id", required = false) String id) {
        List<SysDept> list = sysDeptService.getAllNoLeaderUser(new SysDeptPageDto());
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
    @GetMapping(value = "/query")
    public Result<SysDept> query(@RequestParam(name = "id", required = true) String id) {
        SysDept sysDept = sysDeptService.getInfo(id);
        return Result.ok(sysDept);
    }

    /**
     * 修改
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("system:dept:edit")
    @GetMapping(value = "/edit")
    public Result<Void> edit(@Validated @RequestBody SysDeptUpdDto dto) {
        sysDeptService.editInfo(dto);
        return Result.ok();
    }

    /**
     * 新增
     *
     * @param dto
     * @return
     */
    @SaCheckPermission("system:dept:add")
    @PostMapping("/add")
    public Result<Void> add(@Validated @RequestBody SysDeptAddDto dto) {
        sysDeptService.addInfo(dto);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @SaCheckPermission("system:dept:del")
    @GetMapping(value = "/del")
    public Result<Void> del(@RequestParam(value = "id") String id) {
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
    @GetMapping(value = "/getUserList")
    public Result<List<SysUserPageVo>> getUserList(String id, String keyword) {
        List<SysUserPageVo> userList = sysUserService.getUserList(id, keyword, keyword, keyword, keyword, keyword);
        return Result.ok(userList);
    }
}
