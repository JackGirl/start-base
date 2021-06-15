package cn.ulyer.auth.secure.details;

import cn.hutool.core.bean.BeanUtil;
import cn.ulyer.baseapi.dto.App;
import cn.ulyer.baseapi.dubboapi.AppApi;
import cn.ulyer.common.oauth.Oauth2ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;

public class DaoClientDetailService  implements ClientDetailsService{

    AppApi appClient;

    public DaoClientDetailService(AppApi appClient){
        this.appClient = appClient;
    }

    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        App app = appClient.loadAppById(Long.valueOf(s));
        if(app==null){
            throw new NoSuchClientException("找不到该应用 appId:"+s);
        }
        Oauth2ClientDetails oauth2ClientDetails = new Oauth2ClientDetails();
        BeanUtil.copyProperties(app,oauth2ClientDetails);
        return oauth2ClientDetails;
    }


}
