package cn.ulyer.baseserver.api;


import cn.ulyer.baseclient.entity.BaseUser;
import cn.ulyer.baseserver.service.service.BaseUserService;
import cn.ulyer.common.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
public class BaseUserController  {



    @Autowired
    private BaseUserService  baseUserService;





    @PostMapping("/userLogin")
    public R<BaseUser> login(String account) {
        BaseUser baseUser = baseUserService.getOne(new LambdaQueryWrapper<BaseUser>().eq(BaseUser::getAccount,account));
        return R.success().setData(baseUser);
    }





}

