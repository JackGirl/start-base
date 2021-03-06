package cn.ulyer.baseserver.service.impl;

import cn.ulyer.baseapi.dubboapi.ResourceServerApi;
import cn.ulyer.baseapi.entity.BaseResourceServer;
import cn.ulyer.baseserver.mapper.BaseResourceServerMapper;
import cn.ulyer.baseserver.service.BaseResourceServerService;
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
@DubboService(interfaceClass = ResourceServerApi.class)
public class BaseResourceServerServiceImpl extends ServiceImpl<BaseResourceServerMapper, BaseResourceServer> implements BaseResourceServerService {

}
