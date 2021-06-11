package cn.ulyer.baseserver.api;


import cn.ulyer.baseclient.entity.BaseMenu;
import cn.ulyer.baseclient.vo.MenuVo;
import cn.ulyer.baseserver.service.BaseMenuService;
import cn.ulyer.common.constants.RoleValue;
import cn.ulyer.common.constants.SystemConstants;
import cn.ulyer.common.model.TreeVo;
import cn.ulyer.common.utils.TreeUtil;
import cn.ulyer.common.utils.OauthUtil;
import cn.ulyer.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
@RequestMapping("/baseMenu")
public class BaseMenuController {

    @Autowired
    private BaseMenuService baseMenuService;


    @GetMapping("/userMenu")
    public R<List<TreeVo>> getUserMenus(){
        List<MenuVo> menus = baseMenuService.listUserMenuByUserId(OauthUtil.getUserId());
        return R.success().setData(TreeUtil.treeMenu(menus,0L));
    }

    @GetMapping("/list")
    public R<List<TreeVo>>list(){
        List<MenuVo> allMenus = baseMenuService.listMenuVo();
        return R.success().setData(allMenus);
    }

    @PreAuthorize("hasRole('"+ RoleValue.SUPER_ADMIN +"') ")
    @PostMapping("/newMenu")
    public R createMenu(@RequestBody @Valid BaseMenu baseMenu){
        baseMenu.setMenuId(null);
        baseMenu.setStatus(SystemConstants.STATUS_VALID);
        baseMenuService.save(baseMenu);
        return R.success();
    }

    /**
     * 更新菜单
     * @param menu
     * @return
     */
    @PreAuthorize("hasRole('"+ RoleValue.SUPER_ADMIN +"') ")
    @PostMapping("/update")
    public R updateMenu(@RequestBody @Valid BaseMenu menu){
        baseMenuService.updateById(menu);
        return R.success();
    }

    /**
     * 父子组件全部改为不可用
     * @param menuId
     * @return
     */
    @PreAuthorize("hasRole('"+ RoleValue.SUPER_ADMIN +"') ")
    @PostMapping("/remove")
    public R remove(@RequestParam Long menuId){

        return R.success();
    }


}

