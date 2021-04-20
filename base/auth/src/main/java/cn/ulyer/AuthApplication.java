package cn.ulyer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.cloud.bus.BusAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "cn.ulyer",
        exclude = {RabbitAutoConfiguration.class, BusAutoConfiguration.class})
@EnableFeignClients(basePackages = "cn.ulyer.baseclient.client")
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}
