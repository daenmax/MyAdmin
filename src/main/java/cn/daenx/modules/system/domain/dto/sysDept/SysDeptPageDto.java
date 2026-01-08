package cn.daenx.modules.system.domain.dto.sysDept;

import cn.daenx.framework.common.domain.dto.BasePageDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysDeptPageDto extends BasePageDto {
    private String id;
    private String name;
    private String code;
    private String summary;
    private String remark;
    private String status;
}
