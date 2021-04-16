package cn.ulyer.baseclient.client;

import cn.ulyer.baseclient.constants.BaseConstants;
import cn.ulyer.baseclient.entity.BaseUser;
import cn.ulyer.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(BaseConstants.BASE_SERVER_ID)
@Component
public interface UserClient {


    @PostMapping("/baseUser/userLogin")
    R<BaseUser> login(@RequestParam(value = "account") String account);





}
