package cn.ulyer.common.binder;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface LogInput {

    String INPUT = "logInput";

    @Input(INPUT)
    SubscribableChannel input();

}
