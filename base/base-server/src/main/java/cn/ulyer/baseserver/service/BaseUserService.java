package cn.ulyer.baseserver.service;

import cn.ulyer.baseclient.dto.LoginUser;
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
public interface BaseUserService extends IService<cn.ulyer.baseclient.entity.BaseUser> {

    Page<cn.ulyer.baseclient.entity.BaseUser> pageUserByWrapper(Page<cn.ulyer.baseclient.entity.BaseUser> objectPage, cn.ulyer.baseclient.entity.BaseUser baseUser);

    LoginUser login(String account);
}
