package cn.ulyer.baseserver.api;


import cn.hutool.core.util.StrUtil;
import cn.ulyer.baseapi.entity.BaseApp;
import cn.ulyer.baseapi.entity.BaseAppResource;
import cn.ulyer.baseapi.vo.AuthorityVo;
import cn.ulyer.baseserver.service.BaseAppResourceService;
import cn.ulyer.baseserver.service.BaseAppService;
import cn.ulyer.baseserver.service.BaseResourceService;
import cn.ulyer.common.constants.SystemConstants;
import cn.ulyer.common.utils.PageResult;
import cn.ulyer.common.utils.R;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author mybatis-plus generator
 * @since 2021-04-15
 */
@RestController
@RequestMapping("/baseApp")
public class BaseAppController{

    @Autowired
    private BaseAppService baseAppService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BaseResourceService baseResourceService;

    @Autowired
    private BaseAppResourceService baseAppResourceService;



    @GetMapping("/list")
    public R<List<BaseApp>> list(@RequestParam(value = SystemConstants.PAGE_NAME) Integer page,
                                 @RequestParam(value = SystemConstants.SIZE_PARAM) Integer pageSize,
                                 BaseApp app) {
        LambdaQueryWrapper<BaseApp> wrapper = Wrappers.lambdaQuery();
        if (app != null) {
            wrapper.eq(StrUtil.isNotBlank(app.getAppName()), BaseApp::getAppName, app.getAppName());
            wrapper.eq(Objects.nonNull(app.getAppId()), BaseApp::getAppId, app.getAppId());
        }
        Page<BaseApp> pager = baseAppService.page(new Page<>(page, pageSize), wrapper);
        return R.success().setData(new PageResult<>(pager));
    }

    @PostMapping("/createApp")
    public R createApp(@RequestBody @Valid BaseApp baseApp) {
        baseApp.setAppId(null);
        if (StrUtil.isNotBlank(baseApp.getJsonInformation())) {
            try {
                JSONObject.parseObject(baseApp.getJsonInformation());
            } catch (Exception e) {
                throw new IllegalArgumentException("json信息格式不对");
            }
        }
        baseApp.setAppSecret(passwordEncoder.encode(baseApp.getAppSecret()));
        baseAppService.save(baseApp);
        return R.success();
    }

    @PostMapping("/update")
    public R update(@RequestBody @Valid BaseApp baseApp) {
        baseApp.setAppSecret(null);
        if (StrUtil.isNotBlank(baseApp.getJsonInformation())) {
            try {
                JSONObject.parseObject(baseApp.getJsonInformation());
            } catch (Exception e) {
                throw new IllegalArgumentException("json信息格式不对");
            }
        }
        baseAppService.updateById(baseApp);
        return R.success();
    }

    @GetMapping("/listAppAuthorities")
    public R<List<AuthorityVo>> appAuthorities(@RequestParam Long appId) {
        return R.success().setData(baseResourceService.listAuthorityVoByAppId(appId));
    }


    @PostMapping("/addAppAuthorities")
    public R addAppAuthorities(@RequestParam Long appId, @RequestParam List<Long> authorities) {
        BaseAppResource appResource = new BaseAppResource();
        appResource.setAppId(appId);
        authorities.forEach(authority -> {
            appResource.setId(null);
            appResource.setResourceId(authority);
            baseAppResourceService.remove(Wrappers.<BaseAppResource>lambdaUpdate().eq(BaseAppResource::getAppId, appId).eq(BaseAppResource::getResourceId, authority));
            baseAppResourceService.save(appResource);
        });
        return R.success();
    }

    @PostMapping("/updateAppAuthority")
    public R updateAppAuthorities(@RequestParam(required = false) @DateTimeFormat(pattern = "YYYY-MM-dd HH:mm:ss") Date expireTime,
                                  @RequestParam Long relationId) {
        BaseAppResource appResource = new BaseAppResource();
        appResource.setId(relationId);
        appResource.setExpireTime(expireTime);
        baseAppResourceService.updateById(appResource);
        return R.success();
    }

    @PostMapping("/removeAppAuthorities")
    public R removeAppAuthorities(@RequestParam List<Long> relationIds) {
        baseAppResourceService.removeByIds(relationIds);
        return R.success();
    }


}

