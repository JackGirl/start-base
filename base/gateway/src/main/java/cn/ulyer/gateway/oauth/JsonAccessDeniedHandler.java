package cn.ulyer.gateway.oauth;

import cn.ulyer.common.oauth.GlobalExceptionHandler;
import cn.ulyer.common.utils.R;
import cn.ulyer.gateway.utils.WebFluxUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关权限异常处理,记录日志
 * @author liuyadu
 */
@Slf4j
public class JsonAccessDeniedHandler implements ServerAccessDeniedHandler {

    public JsonAccessDeniedHandler() {
   }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException e) {
        R r = GlobalExceptionHandler.resolveException(e,exchange.getRequest().getURI().getPath());
        return Mono.defer(() -> {
            return Mono.just(exchange.getResponse());
        }).flatMap((response) -> {
            response.setStatusCode(HttpStatus.FORBIDDEN);
           return WebFluxUtils.writeJson(response, JSON.toJSONString(r));
        });
    }
}
