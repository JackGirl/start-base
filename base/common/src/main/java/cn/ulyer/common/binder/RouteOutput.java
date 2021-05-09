package cn.ulyer.common.binder;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface RouteOutput {

    String OUTPUT = "routeOutput";

    @Output(OUTPUT)
    MessageChannel output();

}
