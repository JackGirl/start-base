package cn.ulyer.common.utils;

import cn.ulyer.common.oauth.Oauth2User;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

public class OauthUtil {

    private OauthUtil(){}


    public static Long getUserId(){
        Oauth2User user = getUser();
        return null == user ? null:user.getId();
    }

    public static Oauth2User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof  OAuth2Authentication){
            Object principal = ((OAuth2Authentication) authentication).getUserAuthentication().getPrincipal();
            if(principal instanceof Oauth2User){
                return ((Oauth2User) principal);
            }
        }
        return null;
    }

    /**
     * appModel is long
     * @return
     */
    public static String getClientId(){
        OAuth2Authentication authentication =(OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        return authentication.getOAuth2Request().getClientId();
    }

}
