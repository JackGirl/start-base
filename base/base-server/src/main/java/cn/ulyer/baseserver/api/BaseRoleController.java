package cn.ulyer.baseserver.api;


import cn.ulyer.baseclient.entity.BaseRole;
import cn.ulyer.baseserver.service.service.BaseRoleService;
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
    public R updateRolePermission(JSONObject jsonObject){

        return R.success();
    }




}

