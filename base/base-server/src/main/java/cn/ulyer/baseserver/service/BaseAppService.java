package cn.ulyer.baseserver.service;

import cn.ulyer.baseclient.entity.BaseApp;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mybatis-plus generator
 * @since 2021-04-15
 */
public interface BaseAppService extends IService<BaseApp> {

    void updateAppAndAuthorities(BaseApp baseApp, List<Long> authorities);


}
