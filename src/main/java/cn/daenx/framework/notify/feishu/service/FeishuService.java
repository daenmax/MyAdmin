package cn.daenx.framework.notify.feishu.service;

import cn.daenx.framework.common.vo.system.config.SysFeishuConfigVo;
import cn.daenx.framework.notify.feishu.vo.FeishuSendResult;

public interface FeishuService {
    FeishuSendResult sendMsg(SysFeishuConfigVo configVo, String content);
}
