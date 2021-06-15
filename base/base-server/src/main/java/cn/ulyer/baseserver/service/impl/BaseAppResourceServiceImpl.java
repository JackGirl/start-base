package cn.ulyer.baseserver.service.impl;

import cn.ulyer.baseapi.dubboapi.AppResourceApi;
import cn.ulyer.baseapi.entity.BaseAppResource;
import cn.ulyer.baseserver.mapper.BaseAppResourceMapper;
import cn.ulyer.baseserver.service.BaseAppResourceService;
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
@DubboService(interfaceClass = AppResourceApi.class)
public class BaseAppResourceServiceImpl extends ServiceImpl<BaseAppResourceMapper, BaseAppResource> implements BaseAppResourceService {


}
