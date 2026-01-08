package cn.daenx.data.system.service;

import cn.daenx.data.system.domain.po.SysPositionUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysPositionUserService extends IService<SysPositionUser> {
    /**
     * 更新用户岗位关联信息
     *
     * @param userId
     * @param positionIds
     */
    void handleUserPosition(String userId, List<String> positionIds);

    /**
     * 删除用户岗位关联信息
     *
     * @param userIds
     */
    void delUserPosition(List<String> userIds);

    /**
     * 删除用户的指定岗位
     *
     * @param userId
     * @param positionId
     */
    Boolean delUserPosition(String userId, String positionId);

    /**
     * 给用户添加指定岗位
     *
     * @param userId
     * @param positionId
     * @return
     */
    Boolean addUserPosition(String userId, String positionId);

}
