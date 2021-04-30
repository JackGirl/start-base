package cn.ulyer.baseclient.vo;

import cn.ulyer.baseclient.entity.BaseResource;
import cn.ulyer.baseclient.entity.BaseRole;
import lombok.Data;

import java.util.List;

@Data
public class RoleVo extends BaseRole {

    private List<BaseResource> resources;

    /**
     * 非树形
     */
    private List<MenuVo> menus;

}
