package cn.ulyer.baseserver.service.impl;

import cn.ulyer.baseclient.entity.BaseRole;
import cn.ulyer.baseclient.entity.BaseRoleMenu;
import cn.ulyer.baseserver.mapper.BaseRoleMapper;
import cn.ulyer.baseserver.mapper.BaseRoleMenuMapper;
import cn.ulyer.baseserver.mapper.BaseRoleResourceMapper;
import cn.ulyer.baseserver.service.BaseRoleService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
public class BaseRoleServiceImpl extends ServiceImpl<BaseRoleMapper, BaseRole> implements BaseRoleService {

    @Resource
    private BaseRoleMapper baseRoleMapper;

    @Resource
    private BaseRoleMenuMapper baseRoleMenuMapper;

    @Resource
    private BaseRoleResourceMapper baseRoleResourceMapper;
;

    @Override
    public void updateRoleMenu(BaseRole role,  List<Long> menuId) {
        baseRoleMapper.updateById(role);
        baseRoleMenuMapper.delete(Wrappers.<BaseRoleMenu>lambdaUpdate().eq(BaseRoleMenu::getRoleId,role.getRoleId()));
        BaseRoleMenu roleMenu = new BaseRoleMenu();
        roleMenu.setRoleId(role.getRoleId());
        menuId.forEach(id->{
            roleMenu.setMenuId(id);
            baseRoleMenuMapper.insert(roleMenu);
        });

    }
}
