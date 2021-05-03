package cn.ulyer.baseserver.service;

import cn.ulyer.baseclient.entity.BaseMenu;
import cn.ulyer.baseclient.vo.MenuVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mybatis-plus generator
 * @since 2021-04-15
 */
public interface BaseMenuService extends IService<BaseMenu> {


    List<MenuVo> listUserMenuByUserId(Long userId);


    List<MenuVo> listMenuVo();

    List<MenuVo> listByRoleId(Long roleId);


}
