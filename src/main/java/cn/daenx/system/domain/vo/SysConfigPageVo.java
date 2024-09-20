package cn.daenx.system.domain.vo;

import cn.daenx.common.vo.BasePageVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysConfigPageVo extends BasePageVo {
    private String name;
    private String keyVa;
    private String value;
    private String type;
    private String status;
    private String remark;
}
