package cn.ulyer.common.oauth;

import cn.hutool.core.lang.Assert;
import lombok.Data;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Data
public class RedisTokenServices implements ResourceServerTokenServices {


    private RedisTokenStore tokenStore;

    public RedisTokenServices(RedisTokenStore tokenStore){
        this.tokenStore = tokenStore;
        tokenStore.setSerializationStrategy(new OauthJdkSerialize());
    }

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        Assert.notNull(tokenStore);
        return tokenStore.readAuthentication(accessToken);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {
        Assert.notNull(tokenStore);
        return tokenStore.readAccessToken(accessToken);
    }


}
