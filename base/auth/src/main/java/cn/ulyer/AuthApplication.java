package cn.ulyer;

import com.alibaba.cloud.stream.binder.rocketmq.config.RocketMQComponent4BinderAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "cn.ulyer",
        exclude = {RocketMQComponent4BinderAutoConfiguration.class})
@EnableFeignClients(basePackages = "cn.ulyer.baseclient.client")
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}
