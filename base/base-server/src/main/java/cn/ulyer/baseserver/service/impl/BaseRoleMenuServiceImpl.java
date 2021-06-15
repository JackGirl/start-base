package cn.ulyer.baseserver.service.impl;

import cn.ulyer.baseapi.dubboapi.RoleMenuApi;
import cn.ulyer.baseapi.entity.BaseRoleMenu;
import cn.ulyer.baseserver.mapper.BaseRoleMenuMapper;
import cn.ulyer.baseserver.service.BaseRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mybatis-plus generator
 * @since 2021-04-15
 */
@DubboService(interfaceClass = RoleMenuApi.class)
public class BaseRoleMenuServiceImpl extends ServiceImpl<BaseRoleMenuMapper, BaseRoleMenu> implements BaseRoleMenuService {

}
