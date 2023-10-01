package cn.daenx.framework.common.vo.system.config;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统钉钉配置
 */
@Data
@AllArgsConstructor
public class SysDingTalkConfigVo implements Serializable {

    /**
     * 如果启用了关键词，那么需要填写此参数，会直接拼接在消息前面
     */
    private String keywords;

    /**
     * 如果启用了加签，那么需要填写此参数
     */
    private String secret;

    /**
     * WEBHOOK连接里的参数，必填
     */
    private String accessToken;

    /**
     * 仅仅是备注，无其他作用
     */
    private String remark;

    /**
     * 此参数不是真的存在
     */
    private String botName;
}
