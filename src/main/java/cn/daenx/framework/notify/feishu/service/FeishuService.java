package cn.daenx.framework.notify.feishu.service;

import cn.daenx.framework.common.domain.vo.system.config.SysFeishuConfigVo;
import cn.daenx.framework.notify.feishu.domain.FeishuSendResult;

public interface FeishuService {
    FeishuSendResult sendMsg(SysFeishuConfigVo configVo, String content);
}
