package cn.ulyer.auth.secure;

import cn.ulyer.auth.secure.details.DaoUserDetailService;
import cn.ulyer.common.oauth.JSONAccessDeniedHandler;
import cn.ulyer.common.oauth.JSONAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;


@Configuration
public class SecurityConfig  extends WebSecurityConfigurerAdapter {


    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService(){
        return new DaoUserDetailService();
    }


}
