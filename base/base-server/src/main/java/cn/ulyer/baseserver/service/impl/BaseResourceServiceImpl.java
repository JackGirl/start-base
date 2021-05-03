package cn.ulyer.baseserver.service.impl;

import cn.ulyer.baseclient.entity.BaseResource;
import cn.ulyer.baseclient.vo.ResourceVo;
import cn.ulyer.baseserver.mapper.BaseResourceMapper;
import cn.ulyer.baseserver.service.BaseResourceService;
import cn.ulyer.common.oauth.Oauth2Authority;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

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
@Service
public class BaseResourceServiceImpl extends ServiceImpl<BaseResourceMapper, BaseResource> implements BaseResourceService {

    @Resource
    private BaseResourceMapper baseResourceMapper;

    @Override
    public List<Oauth2Authority> listAutorityByRoles(List<Long> roles){
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
}
