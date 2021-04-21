package cn.ulyer.baseserver.service.service.impl;

import cn.ulyer.baseclient.entity.BaseMenu;
import cn.ulyer.baseserver.mapper.BaseMenuMapper;
import cn.ulyer.baseserver.service.service.BaseMenuService;
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
public class BaseMenuServiceImpl extends ServiceImpl<BaseMenuMapper, BaseMenu> implements BaseMenuService {

    @Resource
    private BaseMenuMapper baseMenuMapper;


    @Override
    public List<BaseMenu> getMenuByUserId(Long userId) {
        return baseMenuMapper.getMenuByUserId(userId);
    }
}
