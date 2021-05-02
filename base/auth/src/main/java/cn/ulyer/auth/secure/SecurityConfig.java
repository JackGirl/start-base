package cn.ulyer.auth.secure;

import cn.ulyer.auth.secure.details.DaoUserDetailService;
import cn.ulyer.auth.secure.phone.PhoneAuthenticationProvider;
import cn.ulyer.baseclient.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;


@Configuration
public class SecurityConfig  extends WebSecurityConfigurerAdapter {


    @Resource
    private UserClient userClient;

    @Autowired
    private PasswordEncoder passwordEncoder;



    /**
     * 短信
     * @return
     */
    public PhoneAuthenticationProvider authenticationProvider(){
        return new PhoneAuthenticationProvider(userClient);
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        auth.authenticationProvider(daoAuthenticationProvider);
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService(){
        return new DaoUserDetailService();
    }



}
