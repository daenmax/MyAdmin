package cn.daenx.common.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 实体类基类
 *
 * @param <T>
 */
@Data
public class BaseEntity<T extends Model<T>> extends Model<T> {

    /**
     * 创建人ID
     */
    @ExcelProperty(value = "创建人ID")
    @TableField(fill = FieldFill.INSERT)
    private String createId;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateId;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    /**
     * 是否删除，0=正常，1=删除
     */
    @TableLogic(value = "0", delval = "1")
    @TableField(value = "is_delete")
    private Integer isDelete;


    /**
     * 创建人名字
     */
    @TableField(exist = false)
    private String createName;

    /**
     * 修改人名字
     */
    @TableField(exist = false)
    private String updateName;

    /**
     * 创建人部门
     */
    @TableField(exist = false)
    private String createDept;
}
