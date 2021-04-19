package cn.ulyer.baseserver.oauth2;

import cn.ulyer.common.oauth.JSONAccessDeniedHandler;
import cn.ulyer.common.oauth.JSONAuthenticationEntryPoint;
import cn.ulyer.common.oauth.RedisTokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;


@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {


    @Autowired
    RedisConnectionFactory redisConnectionFactory;


    @Override
    public void configure(ResourceServerSecurityConfigurer resources)  {
        TokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        RedisTokenServices redisTokenServices = new RedisTokenServices(tokenStore);
        resources.tokenStore(tokenStore).tokenServices(redisTokenServices);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                .antMatchers("/baseUser/userLogin").permitAll()
                // 监控端点内部放行
                .anyRequest().authenticated()
                .and()
                .formLogin().disable()
                // /logout退出清除cookie
                // 认证鉴权错误处理,为了统一异常处理。每个资源服务器都应该加上。
                .exceptionHandling().accessDeniedHandler(new JSONAccessDeniedHandler())
                .authenticationEntryPoint(new JSONAuthenticationEntryPoint())
                .and()
                .csrf().disable()
                // 禁用httpBasic
                .httpBasic().disable();
    }
}
