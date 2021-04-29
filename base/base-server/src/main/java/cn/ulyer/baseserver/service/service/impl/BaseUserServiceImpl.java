package cn.ulyer.baseserver.service.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.ulyer.baseclient.entity.*;
import cn.ulyer.baseclient.dto.LoginUser;
import cn.ulyer.baseserver.mapper.BaseUserMapper;
import cn.ulyer.baseserver.service.service.*;
import cn.ulyer.common.model.AbstractBaseModel;
import cn.ulyer.common.oauth.Oauth2Authority;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mybatis-plus generator
 * @since 2021-04-15
 */
@Service
public class BaseUserServiceImpl extends ServiceImpl<BaseUserMapper, cn.ulyer.baseclient.entity.BaseUser> implements BaseUserService {

    @Resource
    private BaseUserMapper baseUserMapper;

    @Autowired
    private BaseRoleUserService baseRoleUserService;

    @Autowired
    private BaseRoleService baseRoleService;

    @Autowired
    private BaseResourceService baseResourceService;

    @Autowired
    private BaseActionService baseActionService;

    @Override
    public Page<cn.ulyer.baseclient.entity.BaseUser> pageUserByWrapper(Page<cn.ulyer.baseclient.entity.BaseUser> objectPage, cn.ulyer.baseclient.entity.BaseUser baseUser) {
        LambdaQueryWrapper<cn.ulyer.baseclient.entity.BaseUser> wrapper = Wrappers.lambdaQuery();
        if(baseUser!=null){
            wrapper.like(StrUtil.isNotBlank(baseUser.getUsername()), cn.ulyer.baseclient.entity.BaseUser::getUsername,baseUser.getUsername());
            wrapper.like(StrUtil.isNotBlank(baseUser.getAccount()), cn.ulyer.baseclient.entity.BaseUser::getAccount,baseUser.getAccount());
            wrapper.eq(ObjectUtil.isNotNull(baseUser.getEnable()), cn.ulyer.baseclient.entity.BaseUser::getEnable,baseUser.getEnable());
        }
        wrapper.orderByAsc(BaseUser::getId);
        return baseUserMapper.selectPage(objectPage,wrapper);
    }

    @Override
    public LoginUser login(String account) {
        cn.ulyer.baseclient.entity.BaseUser baseUser = baseUserMapper.selectOne(Wrappers.<cn.ulyer.baseclient.entity.BaseUser>lambdaQuery().eq(cn.ulyer.baseclient.entity.BaseUser::getAccount,account));
        if(baseUser==null){
            return null;
        }
        LoginUser loginUser =  new LoginUser();
        BeanUtil.copyProperties(baseUser,loginUser);
        Set<Oauth2Authority> oauth2Authorities = CollectionUtil.newHashSet();
        loginUser.setAuthorities(oauth2Authorities);
        List<BaseRoleUser> userRoles = baseRoleUserService.list(Wrappers.<BaseRoleUser>lambdaQuery().eq(BaseRoleUser::getUserId,loginUser.getId()));
        if(CollectionUtil.isEmpty(userRoles)){
            return loginUser;
        }

        List<BaseRole> roles = baseRoleService.list(Wrappers.<BaseRole>lambdaQuery().in(BaseRole::getRoleId,userRoles.stream().map(BaseRoleUser::getRoleId)
                .collect(Collectors.toList())));
        if(CollectionUtil.isEmpty(roles)){
            return loginUser;
        }

        //resources
        List<Oauth2Authority> resources = baseResourceService.listResourcesByRoles(roles);
        oauth2Authorities.addAll(resources);
        //role
        roles.forEach(role->oauth2Authorities.add(new Oauth2Authority(role.getRoleValue())));
        //actionValue
        List<BaseAction> actions = baseActionService.listActionsByRoles(roles);
        actions.forEach(action->oauth2Authorities.add(new Oauth2Authority(action.getActionValue())));
        return loginUser;
    }
}
