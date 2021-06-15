package cn.ulyer.baseserver.service.impl;

import cn.ulyer.baseapi.dubboapi.MenuApi;
import cn.ulyer.baseapi.entity.BaseMenu;
import cn.ulyer.baseapi.vo.MenuVo;
import cn.ulyer.baseserver.mapper.BaseMenuMapper;
import cn.ulyer.baseserver.service.BaseMenuService;
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
@DubboService(interfaceClass = MenuApi.class)
public class BaseMenuServiceImpl extends ServiceImpl<BaseMenuMapper, BaseMenu> implements BaseMenuService {

    @Resource
    private BaseMenuMapper baseMenuMapper;


    @Override
    public List<MenuVo> listUserMenuByUserId(Long userId) {
        return baseMenuMapper.listUserMenuByUserId(userId);
    }

    @Override
    public List<MenuVo> listMenuVo() {
        return baseMenuMapper.listMenuVo();
    }

    @Override
    public List<MenuVo> listByRoleId(Long roleId) {
        return baseMenuMapper.listByRoleId(roleId);
    }



}
