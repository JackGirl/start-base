package cn.ulyer.auth.secure.details;

import cn.ulyer.baseclient.client.AppClient;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

public class DaoClientDetailService  implements ClientDetailsService{

    AppClient appClient;

    public DaoClientDetailService(AppClient appClient){
        this.appClient = appClient;
    }

    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        return null;
    }


}
