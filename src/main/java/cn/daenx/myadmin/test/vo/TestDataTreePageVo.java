package cn.daenx.myadmin.test.vo;

import cn.daenx.myadmin.common.vo.BasePageVo;
import lombok.Data;

@Data
public class TestDataTreePageVo extends BasePageVo {
    private String title;
    private String content;
    private String type;
    private String status;
    private String remark;
}
