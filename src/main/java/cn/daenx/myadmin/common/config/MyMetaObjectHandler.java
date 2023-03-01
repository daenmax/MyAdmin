package cn.daenx.myadmin.common.config;

import cn.daenx.myadmin.common.utils.LoginUtil;
import cn.daenx.myadmin.system.vo.SysLoginUserVo;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        String userId = getLoginUserId();
        String createTime = "createTime";
        if (metaObject.hasGetter(createTime)) {
            //如果有这个字段
            if (metaObject.getValue(createTime) == null) {
                //如果值是null，再进行填充，否则可能是用户自己在代码里set值了
                this.strictInsertFill(metaObject, createTime, LocalDateTime.class, LocalDateTime.now());
            }
        }
        String createId = "createId";
        if (metaObject.hasGetter(createId)) {
            //如果有这个字段
            if (metaObject.getValue(createId) == null) {
                //如果值是null，再进行填充，否则可能是用户自己在代码里set值了
                if (userId != null) {
                    //用户登录才set
                    this.strictInsertFill(metaObject, createId, String.class, userId);
                }
            }
        }
        String updateTime = "updateTime";
        if (metaObject.hasGetter(updateTime)) {
            //如果有这个字段
            if (metaObject.getValue(updateTime) == null) {
                //如果值是null，再进行填充，否则可能是用户自己在代码里set值了
                this.strictInsertFill(metaObject, updateTime, LocalDateTime.class, LocalDateTime.now());
            }
        }
        String updateId = "updateId";
        if (metaObject.hasGetter(updateId)) {
            //如果有这个字段
            if (metaObject.getValue(updateId) == null) {
                //如果值是null，再进行填充，否则可能是用户自己在代码里set值了
                if (userId != null) {
                    //用户登录才set
                    this.strictInsertFill(metaObject, updateId, String.class, userId);
                }
            }
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String userId = getLoginUserId();
        String updateTime = "updateTime";
        if (metaObject.hasGetter(updateTime)) {
            //如果有这个字段
            this.strictInsertFill(metaObject, updateTime, LocalDateTime.class, LocalDateTime.now());
        }
        String updateId = "updateId";
        if (metaObject.hasGetter(updateId)) {
            //如果有这个字段
            if (userId != null) {
                //用户登录才set
                this.strictInsertFill(metaObject, updateId, String.class, userId);
            }
        }
    }


    /**
     * 获取登录用户ID
     */
    private String getLoginUserId() {
        SysLoginUserVo loginUser;
        try {
            loginUser = LoginUtil.getLoginUser();
        } catch (Exception e) {
            return null;
        }
        return loginUser.getId();
    }
}
