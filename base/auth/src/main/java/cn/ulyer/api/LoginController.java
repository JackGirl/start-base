package cn.ulyer.api;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.ulyer.common.constants.ErrorCode;
import cn.ulyer.common.constants.SystemConstants;
import cn.ulyer.common.utils.R;
import lombok.Data;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

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


    /**
     *  自定义登录 token
     *  主要是当前系统门户使用
     *  手动封装usernamePasswordToken 提交给认证管理器走一遍认证
     *  认证完毕根拿当前app信息组装token 返回
     */
    @PostMapping("/token")
    @ResponseBody
    public R<OAuth2AccessToken> loginToken(@Valid  @RequestBody LoginModel loginModel){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginModel.getAccount(),loginModel.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if(authentication.isAuthenticated()){
            Map<String,String> requestParams = MapUtil.createMap(HashMap.class);
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(SystemConstants.ADMIN_APP);
            if(clientDetails==null){
                throw new NoSuchClientException("找不到应用");
            }
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

    @Data
    static class LoginModel{

        @NotBlank(message = "账号不能为空")
        private String account;

        @NotBlank(message = "密码不能为空")
        private String password;

        public LoginModel(){}

    }

}
