package cn.ulyer.baseserver.api;


import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.ulyer.baseclient.entity.BaseRole;
import cn.ulyer.baseclient.vo.MenuVo;
import cn.ulyer.baseclient.vo.ResourceVo;
import cn.ulyer.baseclient.vo.RoleVo;
import cn.ulyer.baseserver.service.BaseMenuService;
import cn.ulyer.baseserver.service.BaseResourceService;
import cn.ulyer.baseserver.service.BaseRoleService;
import cn.ulyer.common.constants.RoleValue;
import cn.ulyer.common.utils.R;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

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
    R<List<BaseRole>> list(BaseRole role){
        LambdaQueryWrapper<BaseRole> query = Wrappers.lambdaQuery();
        if(role!=null){
            query.eq(StrUtil.isNotBlank(role.getRoleName()),BaseRole::getRoleName,role.getRoleName());
            query.eq(StrUtil.isNotBlank(role.getRoleValue()),BaseRole::getRoleValue,role.getRoleValue());
        }
        query.orderByAsc(BaseRole::getRoleId);
        return R.success().setData(baseRoleService.list(query));
    }


    @PreAuthorize("hasRole('"+ RoleValue.SUPER_ADMIN +"')")
    @PostMapping("/newRole")
    public R createRole(@RequestBody @Valid  BaseRole baseRole){
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
    public R updateRole(@RequestBody @Valid  BaseRole baseRole){
        baseRole.setRoleValue(null);
        return R.instance(baseRoleService.updateById(baseRole));
    }


    /**
     * 更新角色的菜单 和 action
     */
    @PreAuthorize("hasRole('"+RoleValue.SUPER_ADMIN+"')")
    @PostMapping("/updateRolePermissions")
    public R updateRolePermission(@RequestBody JSONObject jsonObject){
        Long roleId = jsonObject.getLong("roleId");
        Assert.notNull(roleId);
        List<Long> menus = jsonObject.getJSONArray("menus").toJavaList(Long.class);
        List<Long> resources = jsonObject.getJSONArray("authorities").toJavaList(Long.class);
        BaseRole role = new BaseRole();
        role.setRoleId(roleId);
        role.setRoleName(jsonObject.getString("roleName"));
        role.setRoleValue(jsonObject.getString("roleValue"));
        baseRoleService.updateRolePermissions(role,menus,resources);
        return R.success();
    }


    /**
     * 角色menu
     */

    @GetMapping("/roleMenus")
    public R<List<MenuVo>> rolePermissions(@RequestParam Long roleId){
       return R.success().setData(baseMenuService.listByRoleId(roleId));
    }


    @GetMapping("/roleResources")
    public R<List<ResourceVo>> roleResources(@RequestParam Long roleId){
        return R.success().setData(baseResourceService.listResourceVoByRoleId(roleId));
    }



}

