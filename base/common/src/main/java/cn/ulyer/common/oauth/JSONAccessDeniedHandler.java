package cn.ulyer.common.oauth;

import cn.ulyer.common.constants.ErrorCode;
import cn.ulyer.common.utils.R;
import cn.ulyer.common.utils.WebUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义访问拒绝
 * @author liuyadu
 */
@Slf4j
public class JSONAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException exception) throws IOException, ServletException {
        R r= R.fail(ErrorCode.ACCESS_DENIED);
        WebUtils.writeJson(response, JSON.toJSONString(r));
    }
}
