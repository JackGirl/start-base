package cn.ulyer.baseserver.service;

import cn.ulyer.baseapi.dubboapi.RoleApi;
import cn.ulyer.baseapi.entity.BaseRole;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mybatis-plus generator
 * @since 2021-04-15
 */
public interface BaseRoleService extends RoleApi {

    void updateRolePermissions(BaseRole role, List<Long> menus, List<Long> resources);
}
