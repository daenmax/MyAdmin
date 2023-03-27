package cn.daenx.myadmin.system.service;

import cn.daenx.myadmin.system.po.SysPositionUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysPositionUserService extends IService<SysPositionUser>{
    /**
     * 更新用户岗位关联信息
     *
     * @param userId
     * @param positionIds
     */
    void handleUserPosition(String userId, List<String> positionIds);

}
