package cn.ulyer.baseclient.client;


import cn.ulyer.baseclient.constants.BaseConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient(value = BaseConstants.BASE_SERVER_ID)
@Component
public interface ResourceServerClient {




}
