package cn.daenx.framework.common.vo;

import cn.daenx.framework.common.exception.MyException;
import cn.daenx.framework.common.utils.SqlUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页参数
 */
@Data
public class BasePageVo {
    /**
     * 当前页
     */
    private Integer pageNum = 1;

    /**
     * 页尺寸
     */
    private Integer pageSize = 10;

    /**
     * 排序字段，多个用,隔开
     */
    private String orderByColumn;

    /**
     * 排序方向：desc、asc，多个用,隔开
     */
    private String isAsc;
    private String startTime;
    private String endTime;
    /**
     * 默认排序字段
     */
    private String DEFAULT_ORDER_BY_COLUMN = "create_time";
    /**
     * 默认排序方向
     */
    private Boolean DEFAULT_ORDER_BY_ASC = Boolean.FALSE;

    /**
     * 获取分页数据
     *
     * @param defaultOrderBy 是否使用默认排序
     * @param <T>
     * @return
     */
    public <T> Page<T> getPage(Boolean defaultOrderBy) {
        Integer pageNum = ObjectUtil.defaultIfNull(getPageNum(), 1);
        Integer pageSize = ObjectUtil.defaultIfNull(getPageSize(), Integer.MAX_VALUE);
        if (pageNum <= 0) {
            pageNum = 1;
        }
        Page<T> page = new Page<>(pageNum, pageSize);
        List<OrderItem> orderItems = buildOrderItem();
        if (CollUtil.isNotEmpty(orderItems)) {
            page.addOrder(orderItems);
        } else {
            if (defaultOrderBy) {
                //默认排序字段
                page.addOrder(new OrderItem().setColumn(DEFAULT_ORDER_BY_COLUMN).setAsc(DEFAULT_ORDER_BY_ASC));
            }
        }
        return page;
    }

    /**
     * 构建排序
     * <p>
     * 支持的用法如下:
     * {isAsc:"asc",orderByColumn:"id"} order by id asc
     * {isAsc:"asc",orderByColumn:"id,createTime"} order by id asc,create_time asc
     * {isAsc:"desc",orderByColumn:"id,createTime"} order by id desc,create_time desc
     * {isAsc:"asc,desc",orderByColumn:"id,createTime"} order by id asc,create_time desc
     */
    private List<OrderItem> buildOrderItem() {
        if (!(ObjectUtil.isNotEmpty(orderByColumn) && ObjectUtil.isNotEmpty(isAsc))) {
            return null;
        }
        String orderBy = SqlUtil.escapeOrderBySql(orderByColumn);
        orderBy = StrUtil.toUnderlineCase(orderBy);

        // 兼容前端排序类型
        isAsc = StringUtils.replaceEach(isAsc, new String[]{"ascending", "descending"}, new String[]{"asc", "desc"});

        String[] orderByArr = orderBy.split(",");
        String[] isAscArr = isAsc.split(",");
        if (isAscArr.length != 1 && isAscArr.length != orderByArr.length) {
            throw new MyException("排序参数有误");
        }

        List<OrderItem> list = new ArrayList<>();
        // 每个字段各自排序
        for (int i = 0; i < orderByArr.length; i++) {
            String orderByStr = orderByArr[i];
            String isAscStr = isAscArr.length == 1 ? isAscArr[0] : isAscArr[i];
            if ("asc".equals(isAscStr)) {
                list.add(OrderItem.asc(orderByStr));
            } else if ("desc".equals(isAscStr)) {
                list.add(OrderItem.desc(orderByStr));
            } else {
                throw new MyException("排序参数有误");
            }
        }
        return list;
    }
}
