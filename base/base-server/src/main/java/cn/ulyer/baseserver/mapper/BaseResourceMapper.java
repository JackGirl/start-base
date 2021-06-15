package cn.ulyer.baseserver.mapper;

import cn.ulyer.baseapi.entity.BaseResource;
import cn.ulyer.baseapi.vo.AuthorityVo;
import cn.ulyer.baseapi.vo.ResourceVo;
import cn.ulyer.common.oauth.Oauth2Authority;
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
public interface BaseResourceMapper extends BaseMapper<BaseResource> {

    List<Oauth2Authority> listResourcesByRoles(@Param("roles") List<Long> roles);

    List<ResourceVo> listResourceVo(ResourceVo resourceVo);

    List<ResourceVo> listResourceVoByRoleId(Long roleId);

    List<Oauth2Authority> listAuthorityByAppId(Long appId);

    List<AuthorityVo> listAuthorityVoByAppId(Long appId);
}
