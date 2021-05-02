package cn.ulyer.common.oauth;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

@Data
public class Oauth2ClientDetails implements ClientDetails {

    private String appId;

    private String appName;

    private String appSecret;

    private String appType;

    private String redirectUri;

    private String scope;

    private String grantTypes;

    private Integer tokenValidSeconds;

    private Integer refreshTokenValidSeconds;

    private Boolean autoApproval;

    private String jsonInformation;

    Set<GrantedAuthority> authorities;

    private Integer internal;

    @Override
    public String getClientId() {
        return appId;
    }

    @Override
    public Set<String> getResourceIds() {
        return null;
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return appSecret;
    }

    @Override
    public boolean isScoped() {
        return false;
    }

    @Override
    public Set<String> getScope() {
        return StrUtil.isBlank(scope)?null:CollectionUtil.newHashSet(scope.split(","));
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return StrUtil.isBlank(grantTypes)?null:CollectionUtil.newHashSet(grantTypes.split(","));
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return StrUtil.isBlank(redirectUri)?null:CollectionUtil.newHashSet(redirectUri.split(","));
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return tokenValidSeconds;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValidSeconds;
    }

    @Override
    public boolean isAutoApprove(String s) {
        return autoApproval;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        try{
            return JSON.parseObject(this.jsonInformation,Map.class);
        }catch (Exception e){
            return MapUtil.newHashMap();
        }
    }
}
