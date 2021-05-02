package cn.ulyer.api;


import cn.ulyer.common.utils.R;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationApi {


    @GetMapping("/me")
    public R<Authentication> me(Authentication authentication){
        return R.success().setData(authentication.getPrincipal());
    }


}
