package cn.ulyer.auth.secure;

import cn.ulyer.auth.secure.phone.PhoneAuthenticationFilter;
import cn.ulyer.auth.secure.phone.PhoneAuthenticationProvider;
import cn.ulyer.auth.secure.phone.PhoneProperties;
import cn.ulyer.baseclient.client.UserClient;
import cn.ulyer.common.oauth.JSONAccessDeniedHandler;
import cn.ulyer.common.oauth.JSONAuthenticationEntryPoint;
import org.checkerframework.checker.units.qual.A;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;


@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class ResourceServerConfig  extends ResourceServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Resource
    private UserClient userClient;

    @Autowired
    private PhoneProperties properties;


    @Bean
    public PhoneAuthenticationFilter phoneAuthenticationFilter(){
        PhoneAuthenticationFilter phoneAuthenticationFilter = new PhoneAuthenticationFilter(properties.getLoginPath());
        phoneAuthenticationFilter.setAuthenticationManager(authenticationManager);
        return phoneAuthenticationFilter;
    }

    /**
     * 短信
     * @return
     */
    @Bean
    public PhoneAuthenticationProvider authenticationProvider(){
        return new PhoneAuthenticationProvider(userClient);
    }





    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/login","/oauth/**","/token",properties.getLoginPath()).permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").and()
                // 认证鉴权错误处理,为了统一异常处理。每个资源服务器都应该加上。
                .exceptionHandling().accessDeniedHandler(new JSONAccessDeniedHandler())
                .authenticationEntryPoint(new JSONAuthenticationEntryPoint())
                .and()
                .csrf().disable()
                // 禁用httpBasic
                .httpBasic().disable();
                http.addFilterAfter(phoneAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class);
    }
}
