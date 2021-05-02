package cn.ulyer.baseserver.api;


import cn.ulyer.baseclient.entity.BaseResourceServer;
import cn.ulyer.baseserver.service.BaseResourceServerService;
import cn.ulyer.common.constants.SystemConstants;
import cn.ulyer.common.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
@RequestMapping("/baseResourceServer")
public class BaseResourceServerController {


    @Autowired
    private BaseResourceServerService baseResourceServerService;

    @PostMapping("/loadResourceServers")
    public R<List<BaseResourceServer>> loadResourceServers(){
        return R.success().setData(baseResourceServerService.list(new LambdaQueryWrapper<BaseResourceServer>().eq(BaseResourceServer::getStatus,SystemConstants.STATUS_VALID))) ;
    }

}

