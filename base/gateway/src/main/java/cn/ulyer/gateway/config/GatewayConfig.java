package cn.ulyer.gateway.config;


import cn.ulyer.gateway.locator.JdbcRouteLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class GatewayConfig {


    @Autowired
    private RouteDefinitionRepository routeDefinitionRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Bean
    public JdbcRouteLocator jdbcRouteLocator(){
        return new JdbcRouteLocator(jdbcTemplate,routeDefinitionRepository);
    }




}
