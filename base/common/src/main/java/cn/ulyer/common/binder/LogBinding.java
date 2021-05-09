package cn.ulyer.common.binder;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface LogBinding {

    String INPUT = "logInput";

    String OUTPUT = "logOutput";

    String TOPIC = "access_log";

    @Input(INPUT)
    SubscribableChannel input();

    @Output(OUTPUT)
    MessageChannel output();

}
