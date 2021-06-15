package cn.ulyer.gateway.oauth;

import cn.hutool.core.collection.CollectionUtil;
import cn.ulyer.baseapi.entity.BaseResource;
import cn.ulyer.common.constants.ErrorCode;
import cn.ulyer.common.constants.RoleValue;
import cn.ulyer.common.constants.SystemConstants;
import cn.ulyer.common.oauth.Oauth2Authority;
import cn.ulyer.gateway.locator.ResourceLocator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.*;


@Slf4j
@Data
public class AccessManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private StaticPathMatcher staticPathMatcher;

    private ResourceLocator resourceLocator;


    public AccessManager(ResourceLocator resourceLocator) {
        this.resourceLocator = resourceLocator;
    }

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext context) {
        log.info("accessManager");

        //请求资源
        String requestPath = context.getExchange().getRequest().getURI().getPath();
        log.info("requestPath :{}", requestPath);
        // 是否直接放行
        if (staticPathMatcher.permit(context.getExchange())) {
            return Mono.just(new AuthorizationDecision(true));
        }

        return mono.map(auth ->
                new AuthorizationDecision(checkAuthorities(context.getExchange(), auth, requestPath))
        ).defaultIfEmpty(new AuthorizationDecision(false));


    }


    /**
     * 检验权限
     *
     * @param exchange
     * @param auth
     * @param requestPath
     * @return
     */
    private boolean checkAuthorities(ServerWebExchange exchange, Authentication auth, String requestPath) {
        Map<String, BaseResource> resourceMap = resourceLocator.getRouterResourceMap();
        BaseResource resource = resourceMap.get(requestPath);
        if (resource == null || !SystemConstants.STATUS_VALID.equals(resource.getStatus())) {
            return false;
        }

        //不公开
        if (!resource.isPublicApi() ) {
            return false;
        }
        //不需要权限验证
        if (!resource.needAuth()) {
            return true;
        }
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        //没权限直接拒绝
        if (CollectionUtil.isEmpty(authorities)) {
            return false;
        }
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        int result = 0;
        while (iterator.hasNext()){
            Oauth2Authority authority = (Oauth2Authority) iterator.next();
            if(authority.getAuthority().equals(RoleValue.ROLE_PREFIX+RoleValue.SUPER_ADMIN)){
                return true;
            }
            if(authority.getAuthority().equals(resource.getAuthority())){
                if(authority.expired()){
                    throw new AccessDeniedException(ErrorCode.ACCESS_DENIED_AUTHORITY_EXPIRED.getMessage());
                }
                result++;
            }

        }

        return result>0;
    }


}
