package cn.ulyer.gateway.oauth;


import cn.ulyer.baseclient.client.ResourceServerClient;
import cn.ulyer.gateway.locator.JdbcRouteLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {


    @Autowired
    private RouteDefinitionRepository routeDefinitionRepository;

    @Autowired
    private ResourceServerClient resourceServerClient;

    @Bean
    public JdbcRouteLocator jdbcRouteLocator(){
        return new JdbcRouteLocator(resourceServerClient,routeDefinitionRepository);
    }

}
