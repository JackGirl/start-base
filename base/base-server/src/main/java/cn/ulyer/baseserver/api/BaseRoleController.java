package cn.ulyer.baseserver.api;


import cn.hutool.core.lang.Assert;
import cn.ulyer.baseclient.entity.BaseRole;
import cn.ulyer.baseclient.vo.MenuVo;
import cn.ulyer.baseserver.service.BaseMenuService;
import cn.ulyer.baseserver.service.BaseResourceService;
import cn.ulyer.baseserver.service.BaseRoleService;
import cn.ulyer.common.constants.RoleValue;
import cn.ulyer.common.utils.R;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mybatis-plus generator
 * @since 2021-04-15
 */
@RestController
@RequestMapping("/baseRole")
public class BaseRoleController {

    @Autowired
    private BaseRoleService baseRoleService;

    @Autowired
    private BaseResourceService baseResourceService;


    @Autowired
    private BaseMenuService baseMenuService;

    @GetMapping("/list")
    R<List<BaseRole>> list(){
        return R.success().setData(baseRoleService.list());
    }


    @PreAuthorize("hasRole('"+ RoleValue.SUPER_ADMIN +"')")
    @PostMapping("/newRole")
    public R createRole(@RequestBody BaseRole baseRole){
        baseRole.setRoleId(null);
        return R.instance( baseRoleService.save(baseRole));
    }

    /**
     * 角色更新
     * @param baseRole
     * @return
     */
    @PreAuthorize("hasRole('"+ RoleValue.SUPER_ADMIN +"')")
    @PostMapping("/updateRole")
    public R updateRole(@RequestBody BaseRole baseRole){
        baseRole.setRoleValue(null);
        return R.instance(baseRoleService.updateById(baseRole));
    }


    /**
     * 更新角色的菜单 和 action
     */
    @PreAuthorize("hasRole('"+RoleValue.SUPER_ADMIN+"')")
    @PostMapping("/updateRolePermission")
    public R updateRolePermission(@RequestBody JSONObject jsonObject){
        Long roleId = jsonObject.getLong("roleId");
        Assert.notNull(roleId);
        String roleName = jsonObject.getString("roleName");
        List<Long> menuId = jsonObject.getJSONArray("menus").toJavaList(Long.class);
        BaseRole role = new BaseRole();
        role.setRoleId(roleId);
        role.setRoleName(roleName);
        baseRoleService.updateRoleMenu(role,menuId);
        return R.success();
    }


    /**
     * 角色已分配 actions 和 menu
     */

    @GetMapping("/roleMenus")
    public R<List<MenuVo>> rolePermissions(@RequestParam Long roleId){
       return R.success().setData(baseMenuService.listByRoleId(roleId));
    }


}

