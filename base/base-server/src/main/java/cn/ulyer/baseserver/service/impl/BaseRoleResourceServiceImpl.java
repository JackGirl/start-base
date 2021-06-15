package cn.ulyer.baseserver.service.impl;

import cn.ulyer.baseapi.dubboapi.RoleResourceApi;
import cn.ulyer.baseapi.entity.BaseRoleResource;
import cn.ulyer.baseserver.mapper.BaseRoleResourceMapper;
import cn.ulyer.baseserver.service.BaseRoleResourceService;
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
@DubboService(interfaceClass = RoleResourceApi.class)
public class BaseRoleResourceServiceImpl extends ServiceImpl<BaseRoleResourceMapper, BaseRoleResource> implements BaseRoleResourceService {

}
