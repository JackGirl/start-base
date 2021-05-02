package cn.ulyer.gateway.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import reactor.core.publisher.Mono;

@Slf4j
public class RedisTokenAuthenticationManager implements ReactiveAuthenticationManager {

    private TokenStore tokenStore ;

    public RedisTokenAuthenticationManager(TokenStore tokenStore){
        this.tokenStore = tokenStore;
    }


    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        BearerTokenAuthenticationToken bearerTokenAuthenticationToken = (BearerTokenAuthenticationToken) authentication;
        Authentication redisAuthentication  = tokenStore.readAuthentication(bearerTokenAuthenticationToken.getToken());
        if(redisAuthentication==null){
            return Mono.error(new InvalidBearerTokenException("token 已失效"));
        }
        redisAuthentication.setAuthenticated(true);
        return Mono.justOrEmpty(redisAuthentication);
    }
}
