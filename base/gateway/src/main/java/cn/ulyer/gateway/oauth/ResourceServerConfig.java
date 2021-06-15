package cn.ulyer.gateway.oauth;

import cn.hutool.core.collection.ConcurrentHashSet;
import cn.ulyer.common.oauth.OauthJdkSerialize;
import cn.ulyer.gateway.locator.JdbcRouteLocator;
import cn.ulyer.gateway.locator.ResourceLocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.oauth2.server.resource.web.server.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


@Configuration
@Slf4j
public class ResourceServerConfig {


    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedisTokenStore tokenStore(){
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        redisTokenStore.setSerializationStrategy(new OauthJdkSerialize());
        return redisTokenStore;
    }



    /**
     * 跨域配置
     */
    public WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            if (CorsUtils.isCorsRequest(request)) {
                HttpHeaders requestHeaders = request.getHeaders();
                ServerHttpResponse response = ctx.getResponse();
                HttpMethod requestMethod = requestHeaders.getAccessControlRequestMethod();
                HttpHeaders headers = response.getHeaders();
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, requestHeaders.getOrigin());
                headers.addAll(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, requestHeaders.getAccessControlRequestHeaders());
                if (requestMethod != null) {
                    headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, requestMethod.name());
                }
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
                headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
                headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "180000L");
                if (request.getMethod() == HttpMethod.OPTIONS) {
                    response.setStatusCode(HttpStatus.OK);
                    return Mono.empty();
                }
            }
            return chain.filter(ctx);
        };
    }


    @Autowired
    private Environment environment;





    @Bean
    public StaticPathMatcher webPathMatcher(){
        StaticPathMatcher staticPathMatcher = new StaticPathMatcher();
       ConcurrentHashSet urls =  Binder.get(environment).bind("ignore.urls", ConcurrentHashSet.class).get();
       staticPathMatcher.setPermitAll(urls);
       return staticPathMatcher;
    }

    @Autowired
    private ResourceLocator resourceLocator;



    @Bean
    public AccessManager accessManager(){
       AccessManager accessManager = new AccessManager(resourceLocator);
       accessManager.setStaticPathMatcher(webPathMatcher());
       return accessManager;
    }


    @Bean
    public JsonAuthenticationEntryPoint entryPoint(){
        return new JsonAuthenticationEntryPoint();
    }


    @Bean
    SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) {
            ServerBearerTokenAuthenticationConverter tokenAuthenticationConverter = new ServerBearerTokenAuthenticationConverter();
            tokenAuthenticationConverter.setAllowUriQueryParameter(true);
            AuthenticationWebFilter authWebFilter =  new AuthenticationWebFilter(new RedisTokenAuthenticationManager(tokenStore()));
            authWebFilter.setServerAuthenticationConverter(tokenAuthenticationConverter);
            authWebFilter.setAuthenticationFailureHandler(new ServerAuthenticationEntryPointFailureHandler(entryPoint()));

        http
                .httpBasic().disable()
                .csrf().disable()
                .logout().disable()
                .formLogin().disable().authorizeExchange()
               .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers("/refreshRoute").permitAll()
                .anyExchange().access(accessManager())
                .and()
                // 跨域过滤器
                .addFilterAt(corsFilter(), SecurityWebFiltersOrder.CORS)
                //oauth2认证过滤器
              .addFilterAt(authWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
        .exceptionHandling().accessDeniedHandler(new JsonAccessDeniedHandler())
        .authenticationEntryPoint((serverWebExchange, e) -> entryPoint().commence(serverWebExchange,e));
        return http.build();
    }
}
