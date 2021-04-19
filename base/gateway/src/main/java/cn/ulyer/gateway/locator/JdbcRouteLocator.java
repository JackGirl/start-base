package cn.ulyer.gateway.locator;

import cn.ulyer.baseclient.client.ResourceServerClient;
import cn.ulyer.common.event.RemoteRefreshRouteEvent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;

@Getter
@Setter
public class JdbcRouteLocator  implements ApplicationEventPublisherAware , ApplicationListener<RemoteRefreshRouteEvent> {

    private ResourceServerClient resourceServerClient;

    private RouteDefinitionRepository routeDefinitionRepository;

    private ApplicationEventPublisher eventPublisher;


    public JdbcRouteLocator (ResourceServerClient resourceServerClient, RouteDefinitionRepository repository){
        this.resourceServerClient = resourceServerClient;
        this.routeDefinitionRepository = repository;
    }



    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    @EventListener(classes = RemoteRefreshRouteEvent.class)
    public void method(RemoteRefreshRouteEvent remoteRefreshRouteEvent){
        System.out.println("接收到消息2");
    }



    @Override
    public void onApplicationEvent(RemoteRefreshRouteEvent remoteRefreshRouteEvent) {
        System.out.println("接收到时间消息处理");
    }
}
