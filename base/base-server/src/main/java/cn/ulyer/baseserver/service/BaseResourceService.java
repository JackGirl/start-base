package cn.ulyer.baseserver.service;

import cn.ulyer.baseapi.dubboapi.ResourceApi;
import cn.ulyer.baseapi.vo.AuthorityVo;
import cn.ulyer.baseapi.vo.ResourceVo;
import cn.ulyer.common.oauth.Oauth2Authority;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mybatis-plus generator
 * @since 2021-04-15
 */
public interface BaseResourceService extends ResourceApi {

    List<Oauth2Authority> listAuthorityByRoles(List<Long> roles);


    List<ResourceVo> listResourceVo(ResourceVo resourceVo);

    List<ResourceVo> listResourceVoByRoleId(Long roleId);

    List<Oauth2Authority> listAuthorityByAppId(Long appId);

    List<AuthorityVo> listAuthorityVoByAppId(Long appId);

}
