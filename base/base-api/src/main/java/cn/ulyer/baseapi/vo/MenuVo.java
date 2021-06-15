package cn.ulyer.baseapi.vo;

import cn.ulyer.baseapi.entity.BaseMenu;
import cn.ulyer.baseapi.entity.BaseResource;
import cn.ulyer.common.model.TreeVo;
import lombok.Data;

import java.util.List;

@Data
public class MenuVo extends BaseMenu implements TreeVo {


    private List<MenuVo> children;

    private List<BaseResource>  resources;


    @Override
    public Long getId() {
        return this.getMenuId();
    }

    @Override
    public void setChildren(List children) {
        this.children = children;
    }



}
