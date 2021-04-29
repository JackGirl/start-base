package cn.ulyer.baseserver.mapper;

import cn.ulyer.baseclient.entity.BaseAction;
import cn.ulyer.baseclient.entity.BaseRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author mybatis-plus generator
 * @since 2021-04-15
 */
public interface BaseActionMapper extends BaseMapper<BaseAction> {

    List<BaseAction> listActionByRoles(@Param("roles")List<BaseRole> roles);


}
