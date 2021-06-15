package cn.ulyer.gateway;

import cn.ulyer.common.binder.RouteBinding;
import cn.ulyer.common.utils.SpringUtils;
import cn.ulyer.gateway.locator.JdbcRouteLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(SpringUtils.class)
@EnableBinding(RouteBinding.class)
public class GatewayApplication implements CommandLineRunner {

    @Autowired
    private JdbcRouteLocator jdbcRouteLocator;

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Override
    public void run(String... args)  {
        jdbcRouteLocator.refresh();
    }
}
