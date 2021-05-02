package cn.ulyer.gateway.api;


import cn.ulyer.common.event.RemoteRefreshRouteEvent;
import cn.ulyer.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Refresh implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    @Autowired
    private BusProperties busProperties;

    @GetMapping("/refreshEvent")
    public R<String> publish(String destination){
        publisher.publishEvent(new RemoteRefreshRouteEvent(this,busProperties.getId(),destination));
        return R.success("刷新网关");
    }



    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
