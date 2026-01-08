package cn.daenx.framework.common.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 通用实体类
 */
@Data
public class ComIdDto {
    @NotBlank(message = "ID不能为空")
    private String id;

}
