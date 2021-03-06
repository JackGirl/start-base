package cn.ulyer.auth.secure.phone;

import cn.ulyer.baseapi.dubboapi.UserApi;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class PhoneAuthenticationProvider implements AuthenticationProvider {

    private UserApi userClient;

    public PhoneAuthenticationProvider(UserApi userClient){
        this.userClient = userClient;
    }


    /**
     * 先从缓存中获取验证码 一分钟有效期
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public UsernamePasswordAuthenticationToken authenticate(Authentication authentication) throws AuthenticationException {
        PhoneAuthenticationToken phoneAuthenticationToken = (PhoneAuthenticationToken) authentication;
        //根据手机号获取用户
        //userClient.login(phoneAuthenticationToken.getPhone());
        System.out.println("手机认证器");

        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(PhoneAuthenticationToken.class);
    }


}
