package cn.ulyer.common.utils;

import cn.ulyer.common.oauth.Oauth2User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

public class OauthUtil {

    private OauthUtil(){}


    public static Long getUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof  OAuth2Authentication){
           Object principal = ((OAuth2Authentication) authentication).getUserAuthentication().getPrincipal();
            if(principal instanceof Oauth2User){
                return ((Oauth2User) principal).getId();
            }
            //clientId
            return (Long) principal;
        }
        return null;
    }
}
