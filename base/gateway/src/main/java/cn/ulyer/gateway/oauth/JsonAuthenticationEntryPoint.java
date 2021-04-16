package cn.ulyer.gateway.oauth;

import cn.ulyer.common.oauth.GlobalExceptionHandler;
import cn.ulyer.common.utils.R;
import cn.ulyer.gateway.utils.WebFluxUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关认证异常处理,记录日志
 *
 * @author liuyadu
 */
@Slf4j
public class JsonAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    public JsonAuthenticationEntryPoint() {
    }

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        R r = GlobalExceptionHandler.resolveException(e,exchange.getRequest().getURI().getPath());
        return Mono.defer(() -> {
            return Mono.just(exchange.getResponse());
        }).flatMap((response) -> {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
           return WebFluxUtils.writeJson(response, JSON.toJSONString(r));
        });
    }
}
