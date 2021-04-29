package cn.ulyer.baseserver.api;


import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.ulyer.baseclient.client.UserClient;
import cn.ulyer.baseclient.dto.LoginUser;
import cn.ulyer.baseclient.entity.BaseUser;
import cn.ulyer.baseserver.service.service.BaseUserService;
import cn.ulyer.common.constants.RoleValue;
import cn.ulyer.common.constants.SystemConstants;
import cn.ulyer.common.utils.PageResult;
import cn.ulyer.common.utils.R;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/baseUser")
public class BaseUserController  implements UserClient {



    @Autowired
    private BaseUserService  baseUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/userLogin")
    @Override
    public R<LoginUser> login(String account) {
        LoginUser baseUser = baseUserService.login(account);
        return R.success().setData(baseUser);
    }

    @GetMapping("/listUser")
    public R<List<cn.ulyer.baseclient.entity.BaseUser>> listUser(cn.ulyer.baseclient.entity.BaseUser baseUser, @RequestParam(SystemConstants.PAGE_NAME) Integer current, @RequestParam(SystemConstants.SIZE_PARAM) Integer pageSize){
        return R.success().setData(new PageResult<>(baseUserService.pageUserByWrapper(new Page<>(current,pageSize),baseUser)));
    }


    @PreAuthorize("hasRole('"+ RoleValue.SUPER_ADMIN +"') ")
    @PostMapping("/update")
    public R update(@RequestBody LoginUser baseUser){
        boolean updateFlag = baseUserService.update(Wrappers.<cn.ulyer.baseclient.entity.BaseUser>lambdaUpdate()
        .eq(cn.ulyer.baseclient.entity.BaseUser::getId, baseUser.getId())
        .set(cn.ulyer.baseclient.entity.BaseUser::getAccountLocked, baseUser.getAccountLocked())
        .set(StrUtil.isNotBlank(baseUser.getAvatar()), cn.ulyer.baseclient.entity.BaseUser::getAvatar, baseUser.getAvatar())
        .set(ObjectUtil.isNotNull(baseUser.getEnable()), cn.ulyer.baseclient.entity.BaseUser::getEnable, baseUser.getEnable())
        .set(cn.ulyer.baseclient.entity.BaseUser::getRemark, baseUser.getRemark())
        .set(StrUtil.isNotBlank(baseUser.getUsername()), cn.ulyer.baseclient.entity.BaseUser::getUsername, baseUser.getUsername()));
        return updateFlag?R.success():R.fail();
    }

    /**
     *
     * @param params
     * @return
     */
    @PostMapping("/resetPassword")
    public R resetPassword(@RequestBody JSONObject params){
        String password = params.getString("password");
        String oldPassword = params.getString("oldPassword");

        return R.success();
    }

    @PreAuthorize("hasRole('"+RoleValue.ADMIN+"')")
    @PostMapping("/newUser")
    public R newUser(@RequestBody JSONObject userJson){
        BaseUser baseUser = userJson.toJavaObject(BaseUser.class);
        BaseUser databaseUser = baseUserService.getOne(Wrappers.<BaseUser>lambdaQuery().eq(BaseUser::getAccount,baseUser.getAccount()));
        Assert.isNull(databaseUser,"用户账号已存在");
        BaseUser saveUser = new BaseUser();
        saveUser.setAccount(userJson.getString("account"));
        saveUser.setUsername(userJson.getString("username"));
        saveUser.setRemark(userJson.getString("remark"));
        saveUser.setPassword(passwordEncoder.encode(userJson.getString("password")));
        baseUserService.save(saveUser);
        return R.success();
    }



}

