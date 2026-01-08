package cn.daenx.modules.system.domain.dto.sysConfig;

import cn.daenx.framework.common.domain.dto.BasePageDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysConfigPageDto extends BasePageDto {
    private String name;
    private String keyVa;
    private String value;
    private String type;
    private String status;
    private String remark;
}
