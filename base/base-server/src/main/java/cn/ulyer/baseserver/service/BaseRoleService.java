package cn.ulyer.baseserver.service;

import cn.ulyer.baseclient.entity.BaseRole;
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
public interface BaseRoleService extends IService<BaseRole> {

    void updateRolePermissions(BaseRole role, List<Long> menus, List<Long> resources);
}
