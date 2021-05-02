package cn.ulyer.auth.secure.details;

import cn.hutool.core.bean.BeanUtil;
import cn.ulyer.baseclient.client.AppClient;
import cn.ulyer.baseclient.entity.BaseApp;
import cn.ulyer.common.oauth.Oauth2ClientDetails;
import cn.ulyer.common.utils.R;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;

public class DaoClientDetailService  implements ClientDetailsService{

    AppClient appClient;

    public DaoClientDetailService(AppClient appClient){
        this.appClient = appClient;
    }

    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        R<BaseApp> res = appClient.loadAppByAppId(s);
        BaseApp app = res.getData();
        if(app==null){
            throw new NoSuchClientException("找不到该应用 appId:"+s);
        }
        Oauth2ClientDetails oauth2ClientDetails = new Oauth2ClientDetails();
        BeanUtil.copyProperties(app,oauth2ClientDetails);
        return oauth2ClientDetails;
    }


}
