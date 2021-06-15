package cn.ulyer.baseserver.service.impl;

import cn.ulyer.baseapi.dubboapi.RoleApi;
import cn.ulyer.baseapi.entity.BaseRole;
import cn.ulyer.baseapi.entity.BaseRoleMenu;
import cn.ulyer.baseapi.entity.BaseRoleResource;
import cn.ulyer.baseserver.mapper.BaseRoleMapper;
import cn.ulyer.baseserver.mapper.BaseRoleMenuMapper;
import cn.ulyer.baseserver.mapper.BaseRoleResourceMapper;
import cn.ulyer.baseserver.service.BaseRoleService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

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
@DubboService(interfaceClass = RoleApi.class)
public class BaseRoleServiceImpl extends ServiceImpl<BaseRoleMapper, BaseRole> implements BaseRoleService {

    @Resource
    private BaseRoleMapper baseRoleMapper;

    @Resource
    private BaseRoleMenuMapper baseRoleMenuMapper;

    @Resource
    private BaseRoleResourceMapper baseRoleResourceMapper;
;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRolePermissions(BaseRole role, List<Long> menus, List<Long> resources) {
        baseRoleMapper.updateById(role);
        baseRoleMenuMapper.delete(Wrappers.<BaseRoleMenu>lambdaUpdate().eq(BaseRoleMenu::getRoleId,role.getRoleId()));
        BaseRoleMenu roleMenu = new BaseRoleMenu();
        roleMenu.setRoleId(role.getRoleId());
        menus.forEach(id->{
            roleMenu.setMenuId(id);
            baseRoleMenuMapper.insert(roleMenu);
        });
        //清空绑定权限
        baseRoleResourceMapper.delete(Wrappers.<BaseRoleResource>lambdaUpdate().eq(BaseRoleResource::getRoleId,role.getRoleId()));
        BaseRoleResource roleResource = new BaseRoleResource();
        roleResource.setRoleId(role.getRoleId());
        resources.forEach(id->{
            roleResource.setResourceId(id);
            baseRoleResourceMapper.insert(roleResource);
        });
    }
}
