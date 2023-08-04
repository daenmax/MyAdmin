package cn.daenx.system.domain.dto;

import cn.daenx.common.vo.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class SysNoticePageDto extends BaseDto {
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 类型，1=通知，2=公告
     */
    private String type;

    /**
     * 状态，0=正常，1=关闭
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

}
