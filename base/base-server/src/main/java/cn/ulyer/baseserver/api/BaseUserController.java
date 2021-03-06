package cn.ulyer.baseserver.api;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.ulyer.baseapi.entity.BaseRole;
import cn.ulyer.baseapi.entity.BaseRoleUser;
import cn.ulyer.baseapi.entity.BaseUser;
import cn.ulyer.baseserver.service.BaseRoleService;
import cn.ulyer.baseserver.service.BaseRoleUserService;
import cn.ulyer.baseserver.service.BaseUserService;
import cn.ulyer.common.constants.RoleValue;
import cn.ulyer.common.constants.SystemConstants;
import cn.ulyer.common.utils.OauthUtil;
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
import java.util.stream.Collectors;

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
public class BaseUserController {



    @Autowired
    private BaseUserService  baseUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BaseRoleService baseRoleService;

    @Autowired
    private BaseRoleUserService baseRoleUserService;




    @GetMapping("/listUser")
    public R<List<cn.ulyer.baseapi.entity.BaseUser>> listUser(cn.ulyer.baseapi.entity.BaseUser baseUser, @RequestParam(SystemConstants.PAGE_NAME) Integer current, @RequestParam(SystemConstants.SIZE_PARAM) Integer pageSize){
        return R.success().setData(new PageResult<>(baseUserService.pageUserByWrapper(new Page<>(current,pageSize),baseUser)));
    }


    @PreAuthorize("hasRole('"+ RoleValue.SUPER_ADMIN +"') ")
    @PostMapping("/update")
    public R update(@RequestBody BaseUser baseUser){
        boolean updateFlag = baseUserService.update(Wrappers.<cn.ulyer.baseapi.entity.BaseUser>lambdaUpdate()
        .eq(cn.ulyer.baseapi.entity.BaseUser::getId, baseUser.getId())
        .set(cn.ulyer.baseapi.entity.BaseUser::getAccountLocked, baseUser.getAccountLocked())
        .set(StrUtil.isNotBlank(baseUser.getAvatar()), cn.ulyer.baseapi.entity.BaseUser::getAvatar, baseUser.getAvatar())
        .set(ObjectUtil.isNotNull(baseUser.getEnable()), cn.ulyer.baseapi.entity.BaseUser::getEnable, baseUser.getEnable())
        .set(cn.ulyer.baseapi.entity.BaseUser::getRemark, baseUser.getRemark())
        .set(StrUtil.isNotBlank(baseUser.getUsername()), cn.ulyer.baseapi.entity.BaseUser::getUsername, baseUser.getUsername()));
        return R.instance(updateFlag);
    }


    @PreAuthorize("hasRole('"+ RoleValue.SUPER_ADMIN +"') ")
    @PostMapping("/updateUserAndRoles")
    public R updateUserRole(@RequestBody JSONObject jsonObject){
        BaseUser baseUser = jsonObject.getObject("user",BaseUser.class);
        Assert.notNull(baseUser.getId());
        baseUser.setPassword(null);
        List<Long> roleIds = jsonObject.getJSONArray("roles").toJavaList(Long.class);
        baseUserService.updateById(baseUser);
        BaseRoleUser roleUser = new BaseRoleUser();
        roleUser.setUserId(baseUser.getId());
        roleIds.forEach(r->{
            Assert.notNull(r);
            roleUser.setRoleId(r);
            baseRoleUserService.save(roleUser);
        });
        return R.success();
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
        Long user = OauthUtil.getUserId();
        BaseUser baseUser = baseUserService.getById(user);
        Assert.notNull(baseUser);
        if(!passwordEncoder.matches(oldPassword,baseUser.getPassword())){
            return R.fail("原密码输入错误");
        }
        baseUser.setPassword(passwordEncoder.encode(password));
        baseUserService.updateById(baseUser);
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


    /**
     * 获取用户的角色
     */
    @GetMapping("/getRolesByUserId")
    public R<List<BaseRole>> roles(@RequestParam Long userId){
        List<BaseRoleUser> baseRoleUsers = baseRoleUserService.list(Wrappers.<BaseRoleUser>lambdaUpdate().eq(BaseRoleUser::getUserId,userId));
        if(CollectionUtil.isEmpty(baseRoleUsers)){
            return R.success().setData(baseRoleUsers);
        }
        return R.success().setData(baseRoleService.listByIds(baseRoleUsers.stream().map(BaseRoleUser::getRoleId).collect(Collectors.toList())));
    }
}

