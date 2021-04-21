package cn.ulyer.baseserver.service.service;

import cn.ulyer.baseclient.entity.BaseMenu;
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
public interface BaseMenuService extends IService<BaseMenu> {


    List<BaseMenu> getMenuByUserId(Long userId);


}
