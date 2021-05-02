package cn.ulyer.baseclient.client;

import cn.ulyer.baseclient.constants.BaseConstants;
import cn.ulyer.baseclient.entity.BaseResource;
import cn.ulyer.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 *  资源权限
 */

@FeignClient(value = BaseConstants.BASE_SERVER_ID)
@Component
public interface ResourceClient{

     @PostMapping("/baseResource/systemResources")
     R<List<BaseResource>> loadSystemResources();

}
