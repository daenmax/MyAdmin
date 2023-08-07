package cn.daenx.system.domain.vo;

import cn.daenx.framework.common.vo.BasePageVo;
import lombok.Data;

@Data
public class SysDictPageVo extends BasePageVo {
    private String name;
    private String code;
    private String status;
}
