package cn.ulyer.baseserver.service;

import cn.ulyer.baseapi.dubboapi.MenuApi;
import cn.ulyer.baseapi.vo.MenuVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mybatis-plus generator
 * @since 2021-04-15
 */
public interface BaseMenuService extends MenuApi {


    List<MenuVo> listUserMenuByUserId(Long userId);


    List<MenuVo> listMenuVo();

    List<MenuVo> listByRoleId(Long roleId);



}
