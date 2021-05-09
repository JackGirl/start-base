package cn.ulyer.gateway.api;


import cn.ulyer.common.binder.RouteBinding;
import cn.ulyer.common.event.RefreshRouteEvent;
import cn.ulyer.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Refresh  {

    @Autowired
    private RouteBinding routeBinding;

    @GetMapping("/refreshRoute")
    public R<String> publish(){
        routeBinding.output().send(MessageBuilder.withPayload(new RefreshRouteEvent("")).setHeader(RefreshRouteEvent.FLAG_NAME,true).build());
        return R.success("刷新网关");
    }



}
