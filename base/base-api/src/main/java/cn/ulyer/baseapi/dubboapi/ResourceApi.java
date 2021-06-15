package cn.ulyer.baseapi.dubboapi;

import cn.ulyer.baseapi.entity.BaseResource;
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
public interface ResourceApi extends IService<BaseResource> {

    List<BaseResource> loadSystemResources();


}
