package cn.ulyer.api;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.ulyer.auth.secure.phone.PhoneAuthenticationToken;
import cn.ulyer.common.constants.ErrorCode;
import cn.ulyer.common.constants.LoginType;
import cn.ulyer.common.constants.SystemConstants;
import cn.ulyer.common.exception.LoginTypeException;
import cn.ulyer.common.utils.R;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AbstractAuthenticationToken;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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
        LoginType loginType = LoginType.resolveType(loginModel.getLoginType());
        Authentication authenticationToken = null;
        switch (loginType){
            case FORM:
                 authenticationToken = new UsernamePasswordAuthenticationToken(loginModel.getAccount(),loginModel.getPassword());
                 break;
            case PHONE:
                authenticationToken = new PhoneAuthenticationToken(loginModel.mobile,loginModel.captcha);
                break;
            case OAUTH2:
            case INVALID:
                throw new LoginTypeException("非法的登陆类型请求");
            default:
        }
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
            oAuth2Authentication.setDetails(clientDetails);
            OAuth2AccessToken accessToken = tokenService.createAccessToken(oAuth2Authentication);
            return R.success().setData(accessToken);
        }
        return R.fail(ErrorCode.UNAUTHORIZED).setMessage("认证失败");
    }




    @GetMapping("/getCode")
    public R<String> sendPhoneCode(@RequestParam @Valid @Pattern(regexp = "^1[0-9]{2}\\d{4}\\d{4}$") String phone){

        return R.success();
    }



    @Data
    static class LoginModel{

        private String account;

        private String password;

        private String loginType = LoginType.FORM.name();

        private String mobile;

        private String captcha;

        public LoginModel(){}

    }




}
