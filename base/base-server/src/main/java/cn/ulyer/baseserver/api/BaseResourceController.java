package cn.ulyer.baseserver.api;


import cn.ulyer.baseclient.client.ResourceClient;
import cn.ulyer.baseclient.entity.BaseResource;
import cn.ulyer.baseserver.service.service.BaseResourceService;
import cn.ulyer.common.constants.SystemConstants;
import cn.ulyer.common.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mybatis-plus generator
 * @since 2021-04-15
 */
@RestController
@RequestMapping("/baseResource")
public class BaseResourceController implements ResourceClient {

    @Autowired
    private BaseResourceService baseResourceService;

    @Override
    @PostMapping
    public R<List<BaseResource>> loadSystemResources() {
        return R.success().setData(baseResourceService.list(new LambdaQueryWrapper<BaseResource>().eq(BaseResource::getStatus, SystemConstants.STATUS_VALID)));
    }
}

