package cn.daenx.data.system.domain.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class SysUserUpdAuthRoleVo {
    @NotBlank(message = "用户ID不能为空")
    private String userId;
    private List<String> roleIds;
}
