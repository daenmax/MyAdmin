package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.system.po.SysFile;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SysFileService extends IService<SysFile> {
    /**
     * 保存文件
     *
     * @param sysFile
     */
    SysFile saveFile(SysFile sysFile);
}
