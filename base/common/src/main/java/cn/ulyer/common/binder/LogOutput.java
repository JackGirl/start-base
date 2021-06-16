package cn.ulyer.common.binder;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface LogOutput {

    String OUTPUT = "logOutput";

    @Output(OUTPUT)
    MessageChannel output();

}
