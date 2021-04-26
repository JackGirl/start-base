package cn.ulyer.baseserver.api;


import cn.ulyer.baseclient.vo.BaseMenuVo;
import cn.ulyer.baseserver.service.service.BaseMenuService;
import cn.ulyer.common.model.TreeVo;
import cn.ulyer.common.utils.TreeUtil;
import cn.ulyer.common.utils.OauthUtil;
import cn.ulyer.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        List<BaseMenuVo> menus = baseMenuService.getMenuByUserId(OauthUtil.getUserId());
        return R.success().setData(TreeUtil.treeMenu(menus,0L));
    }




}

