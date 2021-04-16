package cn.ulyer.baseclient.cliet;

import cn.ulyer.baseclient.constants.BaseConstants;
import cn.ulyer.baseclient.entity.BaseUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(BaseConstants.BASE_SERVER_ID)
@Component
public interface UserClient {


    @PostMapping("/userLogin")
    BaseUser login(@RequestParam String account);



}
