package cn.ulyer.common.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.ulyer.common.model.TreeVo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TreeUtil {

    private TreeUtil(){}


    /**
     * 构造树形数据
     * @param data
     * @param pId
     * @return
     */
    public static <T extends TreeVo>  List<T > treeMenu(List<T> data, Object pId){
        List<T> tree = new ArrayList<>();
        if(CollectionUtil.isEmpty(data)){
            return tree;
        }
        tree.addAll(data.stream().filter(r->r.getParentId().equals(pId)).collect(Collectors.toList()));
        tree.forEach(parent->parent.setChildren(treeMenu(data,parent.getId())));
        return tree;
    }

}
