package cn.ulyer.baseserver.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.ulyer.baseclient.entity.BaseMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TreeUtil {

    private TreeUtil(){}


    public static List<BaseMenu> treeMenu(List<BaseMenu> menus,Long pId){
        List<BaseMenu> tree = new ArrayList<>();
        if(CollectionUtil.isEmpty(menus)){
            return tree;
        }
        tree.addAll(menus.stream().filter(r->r.getParentId().equals(pId)).collect(Collectors.toList()));
        tree.forEach(parent->parent.setChildren(treeMenu(menus,parent.getMenuId())));
        return tree;
    }

}
