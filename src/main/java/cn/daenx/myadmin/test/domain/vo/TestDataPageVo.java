package cn.daenx.myadmin.test.domain.vo;

import cn.daenx.myadmin.common.vo.BasePageVo;
import lombok.Data;

@Data
public class TestDataPageVo extends BasePageVo {
    private String title;
    private String content;
    private String type;
    private String status;
    private String remark;
}
