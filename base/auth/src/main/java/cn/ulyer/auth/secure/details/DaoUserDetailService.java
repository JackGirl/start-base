package cn.ulyer.auth.secure.details;

import cn.hutool.core.bean.BeanUtil;
import cn.ulyer.baseclient.client.UserClient;
import cn.ulyer.baseclient.entity.BaseUser;
import cn.ulyer.common.oauth.Oauth2UserDetails;
import cn.ulyer.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;

@Slf4j
public class DaoUserDetailService implements UserDetailsService {


    @Resource
    private UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        R<BaseUser> rData = userClient.login(s);
        BaseUser baseUser = rData.getData();
        if(baseUser==null){
            throw new UsernameNotFoundException("用户不存在");
        }
        Oauth2UserDetails userDetails = new Oauth2UserDetails();
        BeanUtil.copyProperties(baseUser,userDetails);
        return userDetails;
    }
}
