package cn.ulyer.baseserver.service.impl;

import cn.ulyer.baseclient.entity.BaseApp;
import cn.ulyer.baseclient.entity.BaseAppResource;
import cn.ulyer.baseserver.mapper.BaseAppMapper;
import cn.ulyer.baseserver.mapper.BaseAppResourceMapper;
import cn.ulyer.baseserver.service.BaseAppService;
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
public class BaseAppServiceImpl extends ServiceImpl<BaseAppMapper, BaseApp> implements BaseAppService {


    @Resource
    private BaseAppMapper baseAppMapper;

    @Resource
    private BaseAppResourceMapper baseAppResourceMapper;

    @Override
    public void updateAppAndAuthorities(BaseApp baseApp, List<Long> authorities) {
        baseAppMapper.updateById(baseApp);
        //更新权限
       // baseAppResourceMapper.delete(Wrappers.<BaseAppResource>lambdaUpdate());
    }
}
