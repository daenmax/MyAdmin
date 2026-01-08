package cn.daenx.modules.system.domain.vo.sysNotice;

import cn.daenx.framework.common.domain.vo.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class SysNoticePageVo extends BaseVo {
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
