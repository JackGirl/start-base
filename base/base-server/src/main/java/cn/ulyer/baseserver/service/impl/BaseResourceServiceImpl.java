package cn.ulyer.baseserver.service.impl;

import cn.ulyer.baseapi.dubboapi.ResourceApi;
import cn.ulyer.baseapi.entity.BaseResource;
import cn.ulyer.baseapi.vo.AuthorityVo;
import cn.ulyer.baseapi.vo.ResourceVo;
import cn.ulyer.baseserver.mapper.BaseResourceMapper;
import cn.ulyer.baseserver.service.BaseResourceService;
import cn.ulyer.common.constants.SystemConstants;
import cn.ulyer.common.oauth.Oauth2Authority;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mybatis-plus generator
 * @since 2021-04-15
 */
@DubboService(interfaceClass = ResourceApi.class)
public class BaseResourceServiceImpl extends ServiceImpl<BaseResourceMapper, BaseResource> implements BaseResourceService {

    @Resource
    private BaseResourceMapper baseResourceMapper;

    @Override
    public List<Oauth2Authority> listAuthorityByRoles(List<Long> roles){
        return baseResourceMapper.listResourcesByRoles(roles);
    }

    @Override
    public List<ResourceVo> listResourceVo(ResourceVo resourceVo) {
        return baseResourceMapper.listResourceVo(resourceVo);
    }

    @Override
    public List<ResourceVo> listResourceVoByRoleId(Long roleId) {
        return baseResourceMapper.listResourceVoByRoleId(roleId);
    }

    @Override
    public List<Oauth2Authority> listAuthorityByAppId(Long appId) {
        return baseResourceMapper.listAuthorityByAppId(appId);
    }

    @Override
    public List<AuthorityVo> listAuthorityVoByAppId(Long appId) {
        return baseResourceMapper.listAuthorityVoByAppId(appId);
    }

    @Override
    public List<BaseResource> loadSystemResources() {
        return list(Wrappers.<BaseResource>lambdaQuery().eq(BaseResource::getStatus, SystemConstants.STATUS_VALID));
    }
}
