package cn.ulyer.baseserver.api;


import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.ulyer.baseclient.entity.BaseResourceServer;
import cn.ulyer.baseserver.service.BaseResourceServerService;
import cn.ulyer.common.binder.RouteBinding;
import cn.ulyer.common.binder.RouteOutput;
import cn.ulyer.common.event.RefreshRouteEvent;
import cn.ulyer.common.model.AbstractBaseModel;
import cn.ulyer.common.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
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
@Slf4j
public class BaseResourceServerController {


    @Autowired
    private BaseResourceServerService baseResourceServerService;

    @Autowired
    private RouteOutput routeOutput;


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
        if(baseResourceServerService.getById(resourceServer.getServiceId())!=null){
            throw new IllegalArgumentException("serviceId已存在相同");
        }
        baseResourceServerService.save(resourceServer);
        routeOutput.output().send(MessageBuilder.withPayload(new RefreshRouteEvent(resourceServer)).setHeader(RefreshRouteEvent.FLAG_NAME,true).build());
        return R.success();
    }


    @PostMapping("/updateResourceServer")
    public R updateResourceServer(@RequestBody @Valid BaseResourceServer resourceServer){
        Assert.notBlank(resourceServer.getServiceId());
        baseResourceServerService.updateById(resourceServer);
        routeOutput.output().send(MessageBuilder.withPayload(new RefreshRouteEvent(resourceServer)).setHeader(RefreshRouteEvent.FLAG_NAME,true).build());
        return R.success();
    }




}

