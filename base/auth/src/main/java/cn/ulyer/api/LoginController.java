package cn.ulyer.api;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.ulyer.common.constants.ErrorCode;
import cn.ulyer.common.constants.SystemConstants;
import cn.ulyer.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @Autowired
    private DefaultTokenServices tokenService;

    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private ClientDetailsService clientDetailsService;

    @PostMapping("/token")
    @ResponseBody
    /**
     *  自定义token
     */
    public R<OAuth2AccessToken> loginToken(String account,String password){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(account,password);
        Map<String,String> requestParams = MapUtil.createMap(HashMap.class);
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(SystemConstants.ADMIN_APP);
        if(clientDetails==null){
            throw new OAuth2AuthenticationException(new OAuth2Error("认证失败"));
        }
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if(authentication.isAuthenticated()){
            requestParams.put("client_id",clientDetails.getClientId());
            requestParams.put("grant_type","password");
            OAuth2Request oAuth2Request =
                    new OAuth2Request(requestParams,clientDetails.getClientId(),clientDetails.getAuthorities(),
                            true,clientDetails.getScope(),clientDetails.getResourceIds(),null, CollectionUtil.newHashSet("token"),null);
            OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request,authentication);
            oAuth2Authentication.setAuthenticated(true);
            OAuth2AccessToken accessToken = tokenService.createAccessToken(oAuth2Authentication);
            return R.success().setData(accessToken);
        }
        return R.fail(ErrorCode.UNAUTHORIZED).setMessage("认证失败");
    }

}
