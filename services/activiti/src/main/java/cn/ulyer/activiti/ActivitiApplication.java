package cn.ulyer.activiti;

import cn.ulyer.common.config.WebConfig;
import cn.ulyer.common.oauth.GlobalExceptionHandler;
import com.alibaba.cloud.stream.binder.rocketmq.config.RocketMQComponent4BinderAutoConfiguration;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication(
        exclude ={ SecurityAutoConfiguration.class, RocketMQComponent4BinderAutoConfiguration.class})
@EnableDiscoveryClient
@Import({WebConfig.class, GlobalExceptionHandler.class})
public class ActivitiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiApplication.class, args);
    }

}
