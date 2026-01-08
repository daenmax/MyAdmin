package cn.daenx.data.system.domain.vo;

import cn.daenx.framework.common.vo.BasePageVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysDictDetailPageVo extends BasePageVo {
    private String dictCode;
    private String label;
    private String value;
    private String status;
    private String remark;
}
