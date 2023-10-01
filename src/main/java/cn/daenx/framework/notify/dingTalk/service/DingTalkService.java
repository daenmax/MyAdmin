package cn.daenx.framework.notify.dingTalk.service;

import cn.daenx.framework.common.vo.system.config.SysDingTalkConfigVo;
import cn.daenx.framework.notify.dingTalk.vo.DingTalkSendResult;

public interface DingTalkService {
    DingTalkSendResult sendMsg(SysDingTalkConfigVo configVo, String content);
}
