package cn.ulyer.baseserver.api;


import cn.ulyer.baseclient.client.AppClient;
import cn.ulyer.baseclient.entity.BaseApp;
import cn.ulyer.baseserver.service.BaseAppService;
import cn.ulyer.common.constants.SystemConstants;
import cn.ulyer.common.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mybatis-plus generator
 * @since 2021-04-15
 */
@RestController
@RequestMapping("/baseApp")
public class BaseAppController implements AppClient {

    @Autowired
    private BaseAppService baseAppService;

    @Override
    @GetMapping("/loadAppByAppId")
    public R<BaseApp> loadAppByAppId(String appId) {
        BaseApp baseApp = baseAppService.getOne(new LambdaQueryWrapper<BaseApp>().eq(BaseApp::getAppId,appId).eq(BaseApp::getStatus, SystemConstants.STATUS_VALID));
        return R.success().setData(baseApp);
    }
}

