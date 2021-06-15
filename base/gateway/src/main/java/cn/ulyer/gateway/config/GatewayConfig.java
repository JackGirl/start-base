package cn.ulyer.gateway.config;


import cn.ulyer.baseapi.dubboapi.ResourceApi;
import cn.ulyer.gateway.locator.JdbcRouteLocator;
import cn.ulyer.gateway.locator.ResourceLocator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.nio.charset.StandardCharsets;

@Configuration
public class GatewayConfig {


    @Autowired
    private RouteDefinitionRepository routeDefinitionRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @DubboReference
    private ResourceApi resourceClient;

    @Bean
    public JdbcRouteLocator jdbcRouteLocator(){
        return new JdbcRouteLocator(resourceLocator(),routeDefinitionRepository);
    }



    @Bean
    public ResourceLocator resourceLocator(){
        return new ResourceLocator(resourceClient,jdbcTemplate);
    }

    @Bean
    public HttpMessageConverters jsonConvert(){
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        jackson2HttpMessageConverter.setDefaultCharset(StandardCharsets.UTF_8);
        ObjectMapper o = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        o.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        jackson2HttpMessageConverter.setObjectMapper(o);
        return new HttpMessageConverters(jackson2HttpMessageConverter,new StringHttpMessageConverter());
    }



}
