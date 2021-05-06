package cn.ulyer.baseclient.client;

import cn.ulyer.baseclient.constants.BaseConstants;
import cn.ulyer.baseclient.dto.App;
import cn.ulyer.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(BaseConstants.BASE_SERVER_ID)
@Component
public interface AppClient {

    @GetMapping("/baseApp/loadAppByAppId")
    R<App> loadAppByAppId(@RequestParam("appId") Long appId);

}
