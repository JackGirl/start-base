package cn.ulyer.gateway;

import cn.ulyer.gateway.locator.JdbcRouteLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "cn.ulyer.baseclient.client")
@RemoteApplicationEventScan(basePackages = {"cn.ulyer.common.event"})
public class GatewayApplication implements CommandLineRunner {

    @Autowired
    private JdbcRouteLocator jdbcRouteLocator;

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        jdbcRouteLocator.refresh();
    }
}
