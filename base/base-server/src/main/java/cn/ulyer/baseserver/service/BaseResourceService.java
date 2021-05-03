package cn.ulyer.baseserver.service;

import cn.ulyer.baseclient.entity.BaseResource;
import cn.ulyer.baseclient.entity.BaseRole;
import cn.ulyer.baseclient.vo.ResourceVo;
import cn.ulyer.common.oauth.Oauth2Authority;
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
public interface BaseResourceService extends IService<BaseResource> {

    List<Oauth2Authority> listAutorityByRoles(List<Long> roles);


    List<ResourceVo> listResourceVo(ResourceVo resourceVo);

    List<ResourceVo> listResourceVoByRoleId(Long roleId);

}
