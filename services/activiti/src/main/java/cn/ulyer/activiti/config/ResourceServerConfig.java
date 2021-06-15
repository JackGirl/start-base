package cn.ulyer.activiti.config;

import cn.ulyer.common.oauth.JSONAccessDeniedHandler;
import cn.ulyer.common.oauth.JSONAuthenticationEntryPoint;
import cn.ulyer.common.oauth.RedisTokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;


@EnableResourceServer
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {


    @Autowired
    private RedisConnectionFactory redisConnectionFactory;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin().disable()
                .httpBasic().disable()
                .cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                .antMatchers("/editor/stencilset").permitAll()
                .anyRequest().permitAll()
        .and().exceptionHandling().accessDeniedHandler(new JSONAccessDeniedHandler())
        .authenticationEntryPoint(new JSONAuthenticationEntryPoint());
    }




    @Override
    public void configure(ResourceServerSecurityConfigurer resources)  {
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        RedisTokenServices redisTokenServices = new RedisTokenServices(redisTokenStore);
        resources.tokenStore(redisTokenStore).tokenServices(redisTokenServices);

    }
}
