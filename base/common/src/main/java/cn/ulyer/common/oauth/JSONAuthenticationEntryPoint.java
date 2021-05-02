package cn.ulyer.common.oauth;

import cn.ulyer.common.constants.ErrorCode;
import cn.ulyer.common.utils.R;
import cn.ulyer.common.utils.WebUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义未认证处理
 *
 * @author liuyadu
 */
@Slf4j
public class JSONAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException exception)  {
        R  r= R.fail(ErrorCode.UNAUTHORIZED);
        WebUtils.writeJson(response, JSON.toJSONString(r));
    }
}
