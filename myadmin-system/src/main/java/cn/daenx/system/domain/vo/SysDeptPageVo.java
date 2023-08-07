package cn.daenx.system.domain.vo;

import cn.daenx.framework.common.vo.BasePageVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysDeptPageVo extends BasePageVo {
    private String id;
    private String name;
    private String code;
    private String summary;
    private String remark;
    private String status;
}
