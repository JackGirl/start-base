package cn.ulyer.baseserver.api;


import cn.ulyer.baseclient.client.UserClient;
import cn.ulyer.baseclient.entity.BaseUser;
import cn.ulyer.baseserver.service.service.BaseUserService;
import cn.ulyer.common.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class BaseUserController implements UserClient , ApplicationEventPublisherAware {

    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private BaseUserService  baseUserService;



    @Override
    @PostMapping("/userLogin")
    public R<BaseUser> login(String account) {
        BaseUser baseUser = baseUserService.getOne(new LambdaQueryWrapper<BaseUser>().eq(BaseUser::getAccount,account));
        return R.success().setData(baseUser);
    }


    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}

