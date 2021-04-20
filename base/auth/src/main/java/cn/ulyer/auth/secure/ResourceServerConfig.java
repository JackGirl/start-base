package cn.ulyer.auth.secure;

import cn.ulyer.common.oauth.JSONAccessDeniedHandler;
import cn.ulyer.common.oauth.JSONAuthenticationEntryPoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;


@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class ResourceServerConfig  extends ResourceServerConfigurerAdapter {




    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/login","/oauth/**","/token").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").and()
                // 认证鉴权错误处理,为了统一异常处理。每个资源服务器都应该加上。
                .exceptionHandling().accessDeniedHandler(new JSONAccessDeniedHandler())
                .authenticationEntryPoint(new JSONAuthenticationEntryPoint())
                .and()
                .csrf().disable()
                // 禁用httpBasic
                .httpBasic().disable();
    }
}
