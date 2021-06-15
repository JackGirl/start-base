package cn.ulyer.baseserver.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.ulyer.baseapi.dto.App;
import cn.ulyer.baseapi.dubboapi.AppApi;
import cn.ulyer.baseapi.entity.BaseApp;
import cn.ulyer.baseserver.mapper.BaseAppMapper;
import cn.ulyer.baseserver.service.BaseAppService;
import cn.ulyer.baseserver.service.BaseResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mybatis-plus generator
 * @since 2021-04-15
 */
@DubboService(interfaceClass = AppApi.class)
public class BaseAppServiceImpl extends ServiceImpl<BaseAppMapper, BaseApp> implements BaseAppService {

    @Autowired
    private BaseResourceService baseResourceService;

    @Override
    public App loadAppById(Long appId) {
        BaseApp app = this.getById(appId);
        if(app==null){
            return null;
        }
        App r = new App();
        BeanUtil.copyProperties(app, r);
        r.setAuthorities(CollectionUtil.newHashSet(baseResourceService.listAuthorityByAppId(appId)));
        return r;
    }
}
