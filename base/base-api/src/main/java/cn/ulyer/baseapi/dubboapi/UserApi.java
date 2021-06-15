package cn.ulyer.baseapi.dubboapi;

import cn.ulyer.baseapi.dto.LoginUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mybatis-plus generator
 * @since 2021-04-15
 */
public interface UserApi extends IService<cn.ulyer.baseapi.entity.BaseUser> {

    Page<cn.ulyer.baseapi.entity.BaseUser> pageUserByWrapper(Page<cn.ulyer.baseapi.entity.BaseUser> objectPage, cn.ulyer.baseapi.entity.BaseUser baseUser);

    LoginUser login(String account);
}
