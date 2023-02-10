package cn.daenx.myadmin.common.config;

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
        String createTime = "createTime";
        if (metaObject.hasGetter(createTime)) {
            //如果有这个字段
            if (metaObject.getValue(createTime) == null) {
                //如果值是null，再进行填充，否则可能是用户自己在代码里set值了
                this.strictInsertFill(metaObject, createTime, LocalDateTime.class, LocalDateTime.now());
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
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String updateTime = "updateTime";
        if (metaObject.hasGetter(updateTime)) {
            //如果有这个字段
            this.strictInsertFill(metaObject, updateTime, LocalDateTime.class, LocalDateTime.now());
        }
    }
}