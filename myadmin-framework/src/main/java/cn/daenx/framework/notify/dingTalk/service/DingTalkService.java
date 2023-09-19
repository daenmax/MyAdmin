package cn.daenx.framework.notify.dingTalk.service;

import cn.daenx.framework.common.vo.system.config.SysDingTalkConfigVo;
import cn.daenx.framework.common.vo.system.utils.DingTalkSendResult;

public interface DingTalkService {
    DingTalkSendResult sendMsg(SysDingTalkConfigVo sysSmsConfigVo, String content);
}
