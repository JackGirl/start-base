package cn.ulyer.baseserver.service.service.impl;

import cn.ulyer.baseclient.entity.BaseAction;
import cn.ulyer.baseclient.entity.BaseRole;
import cn.ulyer.baseserver.mapper.BaseActionMapper;
import cn.ulyer.baseserver.service.service.BaseActionService;
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
public class BaseActionServiceImpl extends ServiceImpl<BaseActionMapper, BaseAction> implements BaseActionService {

    @Resource
    private BaseActionMapper baseActionMapper;


    @Override
    public List<BaseAction> listActionsByRoles(List<BaseRole> roles) {
        return baseActionMapper.listActionByRoles(roles);
    }
}
