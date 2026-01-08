package cn.daenx.framework.notify.email.service;

import cn.daenx.framework.common.domain.vo.system.config.SysEmailConfigVo;

import java.io.File;
import java.util.List;

public interface EmailService {
    Boolean sendMail(SysEmailConfigVo.Email email, String toEmail, String subject, String content, Boolean isHtml, List<File> fileList);
}
