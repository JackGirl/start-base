package cn.ulyer.baseclient.client;


import cn.ulyer.baseclient.constants.BaseConstants;
import cn.ulyer.baseclient.entity.BaseResourceServer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = BaseConstants.BASE_SERVER_ID)
@Component
public interface ResourceServerClient {


    @PostMapping("/baseResourceServer/loadResourceServers")
    List<BaseResourceServer> loadResourceServers();


}
