package cn.ulyer.baseserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "cn.ulyer")
@MapperScan(basePackages = "cn.ulyer.baseserver.mapper")
@RemoteApplicationEventScan(basePackages = {"cn.ulyer.common.event"})
public class BaseServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseServerApplication.class, args);
    }

}
