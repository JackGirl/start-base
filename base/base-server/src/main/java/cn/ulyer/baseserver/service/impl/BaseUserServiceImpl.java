package cn.ulyer.baseserver.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.ulyer.baseapi.dto.LoginUser;
import cn.ulyer.baseapi.dubboapi.UserApi;
import cn.ulyer.baseapi.entity.BaseRole;
import cn.ulyer.baseapi.entity.BaseRoleUser;
import cn.ulyer.baseapi.entity.BaseUser;
import cn.ulyer.baseserver.mapper.BaseUserMapper;
import cn.ulyer.baseserver.service.BaseResourceService;
import cn.ulyer.baseserver.service.BaseRoleService;
import cn.ulyer.baseserver.service.BaseRoleUserService;
import cn.ulyer.baseserver.service.BaseUserService;
import cn.ulyer.common.oauth.Oauth2Authority;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

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
@DubboService(interfaceClass = UserApi.class)
public class BaseUserServiceImpl extends ServiceImpl<BaseUserMapper, cn.ulyer.baseapi.entity.BaseUser> implements BaseUserService {

    @Resource
    private BaseUserMapper baseUserMapper;

    @Autowired
    private BaseRoleUserService baseRoleUserService;

    @Autowired
    private BaseRoleService baseRoleService;

    @Autowired
    private BaseResourceService baseResourceService;


    @Override
    public Page<cn.ulyer.baseapi.entity.BaseUser> pageUserByWrapper(Page<cn.ulyer.baseapi.entity.BaseUser> objectPage, cn.ulyer.baseapi.entity.BaseUser baseUser) {
        LambdaQueryWrapper<cn.ulyer.baseapi.entity.BaseUser> wrapper = Wrappers.lambdaQuery();
        if(baseUser!=null){
            wrapper.like(StrUtil.isNotBlank(baseUser.getUsername()), cn.ulyer.baseapi.entity.BaseUser::getUsername,baseUser.getUsername());
            wrapper.like(StrUtil.isNotBlank(baseUser.getAccount()), cn.ulyer.baseapi.entity.BaseUser::getAccount,baseUser.getAccount());
            wrapper.eq(ObjectUtil.isNotNull(baseUser.getEnable()), cn.ulyer.baseapi.entity.BaseUser::getEnable,baseUser.getEnable());
        }
        wrapper.orderByAsc(BaseUser::getId);
        return baseUserMapper.selectPage(objectPage,wrapper);
    }

    @Override
    public LoginUser login(String account) {
        cn.ulyer.baseapi.entity.BaseUser baseUser = baseUserMapper.selectOne(Wrappers.<cn.ulyer.baseapi.entity.BaseUser>lambdaQuery().eq(cn.ulyer.baseapi.entity.BaseUser::getAccount,account));
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
        List<Long> roleId = roles.stream().map(BaseRole::getRoleId).collect(Collectors.toList());
        //resources
        List<Oauth2Authority> resources = baseResourceService.listAuthorityByRoles(roleId);
        oauth2Authorities.addAll(resources);
        //role
        roles.forEach(role->oauth2Authorities.add(new Oauth2Authority(role.getRoleValue())));
        return loginUser;
    }
}
