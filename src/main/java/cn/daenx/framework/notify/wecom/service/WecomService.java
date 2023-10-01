package cn.daenx.framework.notify.wecom.service;

import cn.daenx.framework.common.vo.system.config.SysWecomConfigVo;
import cn.daenx.framework.notify.wecom.vo.WecomSendResult;

public interface WecomService {
    WecomSendResult sendMsg(SysWecomConfigVo configVo, String content);
}
