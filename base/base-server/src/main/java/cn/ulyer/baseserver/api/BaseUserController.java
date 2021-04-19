package cn.ulyer.baseserver.api;


import cn.ulyer.baseclient.client.UserClient;
import cn.ulyer.baseclient.entity.BaseUser;
import cn.ulyer.baseserver.service.service.BaseUserService;
import cn.ulyer.common.event.RemoteRefreshRouteEvent;
import cn.ulyer.common.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.context.*;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mybatis-plus generator
 * @since 2021-04-15
 */
@RestController
@RequestMapping("/baseUser")
public class BaseUserController implements  ApplicationEventPublisherAware {

    ApplicationEventPublisher applicationEventPublisher;


    @Autowired
    private BaseUserService  baseUserService;

    @Autowired
    private BusProperties busProperties;



    @PostMapping("/userLogin")
    public R<BaseUser> login(String account, HttpServletRequest request) {

        this.applicationEventPublisher.publishEvent(new RemoteRefreshRouteEvent(this,busProperties.getId(),null));
        BaseUser baseUser = baseUserService.getOne(new LambdaQueryWrapper<BaseUser>().eq(BaseUser::getAccount,account));
        return R.success().setData(baseUser);
    }


    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @EventListener(classes = RemoteRefreshRouteEvent.class)
    public void method(RemoteRefreshRouteEvent remoteRefreshRouteEvent){
        System.out.println("baseserver 接收到消息");
    }


}

