package cn.daenx.myadmin.system.po;

import cn.daenx.myadmin.common.vo.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 岗位用户关联表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_position_user")
public class SysPositionUser implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 岗位ID
     */
    @TableField(value = "position_id")
    private String positionId;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private String userId;
}
