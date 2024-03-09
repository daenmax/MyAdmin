package cn.daenx.framework.common.utils;

import cn.daenx.framework.common.vo.TreeEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 树形结构工具类
 *
 * @author gltqe
 * @date 2022/7/3 0:33
 **/
public class TreeUtil {
    /**
     * 构建树形结构（已知topId情况可使用此方法）
     *
     * @param list
     * @param topId
     * @return java.util.List<T>
     * @author gltqe
     * @date 2022/7/3 0:33
     **/
    public static <T extends TreeEntity> List<T> buildTree(List<T> list, String topId) {
        Map<String, List<T>> map = list.stream().collect(Collectors.groupingBy(TreeEntity::getParentId));
        for (Map.Entry<String, List<T>> entry : map.entrySet()) {
            List<T> value = entry.getValue();
            for (TreeEntity treeEntity : value) {
                treeEntity.setChildren(map.get(treeEntity.getId()));
            }
        }
        if (StringUtils.isNotBlank(topId)) {
            return map.get(topId);
        } else {
            return list;
        }
    }

    /**
     * 构建树形结构
     *
     * @param list
     * @return java.util.List<T>
     * @author gltqe
     * @date 2022/7/3 0:34
     **/
    public static <T extends TreeEntity> List<T> buildTree(List<T> list) {
        List<String> ids = new ArrayList<>();
        List<String> pids = new ArrayList<>();
        for (T t : list) {
            String id = t.getId();
            String parentId = t.getParentId();
            ids.add(id);
            pids.add(parentId);
        }
        List<T> result = new ArrayList<>();
        for (T t : list) {
            String parentId = t.getParentId();
            // 如果ids中不包含这个机构的父id  那么它就是顶级机构
            if (!ids.contains(parentId)) {
                List<T> children = getChildren(t, list, pids);
                if (children.size() > 0) {
                    t.setChildren(children);
                }
                result.add(t);
            }
        }
        return result;

    }

    private static <T extends TreeEntity> List<T> getChildren(T p, List<T> list, List<String> pids) {
        List<T> children = new ArrayList<>();
        String pId = p.getId();
        for (T t : list) {
            String parentId = t.getParentId();
            if (pId.equals(parentId)) {
                String id = t.getId();
                if (pids.contains(id)) {
                    //如果父id集合中包含这个id 那么它还有子机构
                    List<T> tList = getChildren(t, list, pids);
                    t.setChildren(tList);
                }
                children.add(t);
            }
        }
        return children;
    }

}
