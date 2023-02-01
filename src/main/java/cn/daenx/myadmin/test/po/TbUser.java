package cn.daenx.myadmin.test.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tb_user")
public class TbUser implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private Integer id;

    @TableField(value = "age")
    private Integer age;

    @TableField(value = "`name`")
    private String name;

    @TableField(value = "create_id")
    private String createId;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "update_id")
    private String updateId;

    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 0=正常，1=删除
     */
    @TableField(value = "is_delete")
    private Integer isDelete;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_AGE = "age";

    public static final String COL_NAME = "name";

    public static final String COL_CREATE_ID = "create_id";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_ID = "update_id";

    public static final String COL_UPDATE_TIME = "update_time";

    public static final String COL_IS_DELETE = "is_delete";
}