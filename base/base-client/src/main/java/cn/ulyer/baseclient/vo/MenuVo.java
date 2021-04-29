package cn.ulyer.baseclient.vo;

import cn.ulyer.baseclient.entity.BaseMenu;
import cn.ulyer.common.model.TreeVo;
import lombok.Data;

import java.util.List;

@Data
public class MenuVo extends BaseMenu implements TreeVo<Long, MenuVo> {


    private List<MenuVo> children;

    private List<ActionVo> actions;

    @Override
    public Long getId() {
        return this.getMenuId();
    }

    @Override
    public void setChildren(List<MenuVo> children) {
        this.children = children;
    }



}
