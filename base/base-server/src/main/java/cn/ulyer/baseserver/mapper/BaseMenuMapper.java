package cn.ulyer.baseserver.mapper;

import cn.ulyer.baseclient.entity.BaseMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author mybatis-plus generator
 * @since 2021-04-15
 */
public interface BaseMenuMapper extends BaseMapper<BaseMenu> {

    List<BaseMenu> getMenuByUserId(Long userId);
}
