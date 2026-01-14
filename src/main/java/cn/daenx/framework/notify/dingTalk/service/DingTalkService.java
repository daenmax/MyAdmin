package cn.daenx.framework.notify.dingTalk.service;

import cn.daenx.framework.common.domain.vo.system.config.SysDingTalkConfigVo;
import cn.daenx.framework.notify.dingTalk.domain.DingTalkSendResult;

public interface DingTalkService {
    DingTalkSendResult sendMsg(SysDingTalkConfigVo configVo, String content);
}
