package cn.ulyer.gateway.oauth;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Data
public class AccessManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private StaticPathMatcher staticPathMatcher;



    public AccessManager (){

    }

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext context) {
        log.info("accessManager");

        //请求资源
        String requestPath = context.getExchange().getRequest().getURI().getPath();
        log.info("requestPath :{}",requestPath);
        // 是否直接放行
        if (staticPathMatcher.permit(context.getExchange())) {
            return Mono.just(new AuthorizationDecision(true));
        }

        return mono.map(auth ->
            new AuthorizationDecision(checkAuthorities(context.getExchange(), auth, requestPath))
        ).defaultIfEmpty(new AuthorizationDecision(false));


    }






    //权限校验
    private boolean checkAuthorities(ServerWebExchange exchange, Authentication auth, String requestPath) {
        if(auth instanceof OAuth2Authentication){
            OAuth2Authentication athentication = (OAuth2Authentication) auth;
            String clientId = athentication.getOAuth2Request().getClientId();
            log.info("clientId is {}",clientId);
        }

        Object principal = auth.getPrincipal();
        log.info("用户信息:{}",principal.toString());
        return true;
    }



}
