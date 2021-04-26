package cn.ulyer.baseclient.vo;

import cn.ulyer.common.model.TreeVo;
import lombok.Data;

import java.util.List;

@Data
public class BaseMenuVo implements TreeVo<Long,BaseMenuVo> {

    private Long menuId;

    private String menuName;

    private Long parentId;

    private String path;

    private String component;

    private String icon;

    private String target;

    private String redirect;

    private List<BaseMenuVo> children;

    private List<BaseActionVo> actions;

    @Override
    public Long getId() {
        return menuId;
    }

    @Override
    public void setChildren(List<BaseMenuVo> children) {
        this.children = children;
    }



}
