package cn.ulyer.api;


import cn.ulyer.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
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
    private OAuth2RequestFactory requestFactory ;

    @PostMapping("/token")
    @ResponseBody
    /**
     *  这里暂时有点问题 创建的authentication没有认证成功
     */
    public R<OAuth2AccessToken> loginToken(String account,String password){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(account,password);
        Set<String> scopes = new HashSet<>();
        scopes.add("read");
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        AuthorizationRequest authorizationRequest = new AuthorizationRequest("121321412",scopes);
        OAuth2Request oAuth2Request =  requestFactory.createOAuth2Request(authorizationRequest);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request,authentication);
        oAuth2Authentication.setAuthenticated(true);
        OAuth2AccessToken accessToken = tokenService.createAccessToken(oAuth2Authentication);
        return R.success().setData(accessToken);
    }

}
