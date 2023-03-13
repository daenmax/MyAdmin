package cn.daenx.myadmin.system.vo;

import cn.daenx.myadmin.common.vo.BasePageVo;
import lombok.Data;

@Data
public class SysDictPageVo extends BasePageVo {
    private String name;
    private String code;
    private String status;
}
