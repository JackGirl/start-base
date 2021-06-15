package cn.ulyer.baseserver.service.impl;

import cn.ulyer.baseapi.dubboapi.RoleUserApi;
import cn.ulyer.baseapi.entity.BaseRoleUser;
import cn.ulyer.baseserver.mapper.BaseRoleUserMapper;
import cn.ulyer.baseserver.service.BaseRoleUserService;
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
@DubboService(interfaceClass = RoleUserApi.class)
public class BaseRoleUserServiceImpl extends ServiceImpl<BaseRoleUserMapper, BaseRoleUser> implements BaseRoleUserService {

}
