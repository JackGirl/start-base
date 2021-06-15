package cn.ulyer.baseapi.dubboapi;

import cn.ulyer.baseapi.dto.App;
import cn.ulyer.baseapi.entity.BaseApp;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mybatis-plus generator
 * @since 2021-04-15
 */
public interface AppApi extends IService<BaseApp> {


    App loadAppById(Long appId);


}
