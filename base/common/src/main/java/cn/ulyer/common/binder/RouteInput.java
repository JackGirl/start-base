package cn.ulyer.common.binder;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface RouteInput {

    String INPUT = "routeInput";


    @Input(INPUT)
    SubscribableChannel input();

}
