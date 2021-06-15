package cn.ulyer.baseapi.vo;

import cn.ulyer.baseapi.entity.BaseResource;
import cn.ulyer.baseapi.entity.BaseRole;
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
