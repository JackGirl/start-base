package cn.ulyer.baseserver.api;


import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.ulyer.baseclient.entity.BaseResourceServer;
import cn.ulyer.baseserver.service.BaseResourceServerService;
import cn.ulyer.common.constants.SystemConstants;
import cn.ulyer.common.event.RemoteRefreshRouteEvent;
import cn.ulyer.common.model.AbstractBaseModel;
import cn.ulyer.common.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * 资源服务器管理  主要对路由有影响  动态路由等
 * @author mybatis-plus generator
 * @since 2021-04-15
 */
@RestController
@RequestMapping("/baseResourceServer")
public class BaseResourceServerController implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private BusProperties busProperties;

    @Autowired
    private BaseResourceServerService baseResourceServerService;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/loadResourceServers")
    public R<List<BaseResourceServer>> loadResourceServers(BaseResourceServer baseResourceServer){
        LambdaQueryWrapper<BaseResourceServer> wrapper = Wrappers.lambdaQuery();
        if(baseResourceServer!=null){
            wrapper.eq(StrUtil.isNotBlank(baseResourceServer.getServiceId()),BaseResourceServer::getServiceId,baseResourceServer.getServiceId());
            wrapper.eq(StrUtil.isNotBlank(baseResourceServer.getServiceName()),BaseResourceServer::getServiceName,baseResourceServer.getServiceName());
        }
        wrapper.orderByDesc(AbstractBaseModel::getCreateTime);
        return R.success().setData(baseResourceServerService.list(wrapper)) ;
    }


    /**
     * 添加路由
     */
    @PostMapping("/addResourceServer")
    public R addResourceServer(@RequestBody @Valid BaseResourceServer resourceServer){
        baseResourceServerService.save(resourceServer);
        eventPublisher.publishEvent(new RemoteRefreshRouteEvent(this,busProperties.getId(),null));
        return R.success();
    }


    @PostMapping("/updateResourceServer")
    public R updateResourceServer(@RequestBody @Valid BaseResourceServer resourceServer){
        Assert.notBlank(resourceServer.getServiceId());
        baseResourceServerService.updateById(resourceServer);
        eventPublisher.publishEvent(new RemoteRefreshRouteEvent(this,busProperties.getId(),null));
        return R.success();
    }





}

