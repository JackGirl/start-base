package cn.ulyer.auth.secure.details;

import cn.hutool.core.bean.BeanUtil;
import cn.ulyer.baseapi.dto.LoginUser;
import cn.ulyer.baseapi.dubboapi.UserApi;
import cn.ulyer.common.oauth.Oauth2User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;

@Slf4j
public class DaoUserDetailService implements UserDetailsService {


    @Resource
    private UserApi userClient;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        LoginUser user  = userClient.login(s);
        if(user==null){
            throw new UsernameNotFoundException("用户不存在");
        }
        Oauth2User oauth2User = new Oauth2User();
        BeanUtil.copyProperties(user,oauth2User);
        return oauth2User;
    }
}
