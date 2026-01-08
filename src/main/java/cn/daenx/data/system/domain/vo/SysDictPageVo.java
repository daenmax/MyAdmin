package cn.daenx.data.system.domain.vo;

import cn.daenx.framework.common.vo.BasePageVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysDictPageVo extends BasePageVo {
    private String name;
    private String code;
    private String status;
}
