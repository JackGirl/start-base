package cn.ulyer.baseserver.api;


import cn.hutool.core.lang.Assert;
import cn.ulyer.baseclient.client.ResourceClient;
import cn.ulyer.baseclient.entity.BaseResource;
import cn.ulyer.baseclient.entity.BaseResourceServer;
import cn.ulyer.baseclient.vo.ResourceVo;
import cn.ulyer.baseserver.service.BaseResourceServerService;
import cn.ulyer.baseserver.service.BaseResourceService;
import cn.ulyer.common.constants.SystemConstants;
import cn.ulyer.common.utils.R;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mybatis-plus generator
 * @since 2021-04-15
 */
@RestController
@RequestMapping("/baseResource")
public class BaseResourceController implements ResourceClient {

    @Autowired
    private BaseResourceServerService baseResourceServerService;

    @Autowired
    private BaseResourceService baseResourceService;

    @Override
    @PostMapping("/baseResource/systemResources")
    public R<List<BaseResource>> loadSystemResources() {
        return R.success().setData(baseResourceService.list(Wrappers.<BaseResource>lambdaQuery().eq(BaseResource::getStatus, SystemConstants.STATUS_VALID)));
    }


    @GetMapping("/list")
    public R<List<ResourceVo>> list(ResourceVo resourceVo){
        return R.success().setData(baseResourceService.listResourceVo(resourceVo));
    }

    @PostMapping("/createResource")
    public R createResource(@RequestBody @Valid BaseResource baseResource){
        BaseResource databaseResource = baseResourceService.getOne(Wrappers.<BaseResource>lambdaQuery().eq(BaseResource::getServiceId,baseResource.getServiceId())
                .eq(BaseResource::getPath,baseResource.getPath()),false);
        Assert.isNull(databaseResource,"已存在资源接口");
        BaseResourceServer baseResourceServer = baseResourceServerService.getById(baseResource.getServiceId());
        Assert.notNull(baseResourceServer,"未找到资源服务器");
        baseResource.setStatus(SystemConstants.STATUS_VALID);
        baseResourceService.save(baseResource);
        return R.success();
    }

    @PostMapping("/edit")
    public R edit(@RequestBody @Valid  BaseResource baseResource){
        BaseResourceServer baseResourceServer = baseResourceServerService.getById(baseResource.getServiceId());
        Assert.notNull(baseResourceServer,"未找到资源服务器");
        baseResourceService.updateById(baseResource);
        return R.success();
    }





}

