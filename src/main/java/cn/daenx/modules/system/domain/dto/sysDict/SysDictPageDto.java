package cn.daenx.modules.system.domain.dto.sysDict;

import cn.daenx.framework.common.domain.dto.BasePageDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysDictPageDto extends BasePageDto {
    private String name;
    private String code;
    private String status;
}
