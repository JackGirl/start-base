package cn.ulyer.auth.secure.phone;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 手机登录接口
 */
public class PhoneAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    public PhoneAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl,"POST"));
    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String phone = httpServletRequest.getParameter("phone");
        if(phone==null){
            phone="";
        }
        String code = httpServletRequest.getParameter("code");
        PhoneAuthenticationToken phoneAuthenticationToken = new PhoneAuthenticationToken(phone,code);
        return super.getAuthenticationManager().authenticate(phoneAuthenticationToken);
    }
}
