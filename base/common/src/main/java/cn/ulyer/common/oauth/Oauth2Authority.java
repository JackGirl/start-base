package cn.ulyer.common.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.AntPathMatcher;

import java.util.Date;

@Data
public class Oauth2Authority implements GrantedAuthority {


    private String resourceId;

    private String authority;

    private Date expireTime;

    public Oauth2Authority(){}


    public Oauth2Authority(String resourceId,String authority,Date expireTime){
        this.resourceId = resourceId;
        this.authority = authority;
        this.expireTime = expireTime;
    }

    public Oauth2Authority(String roleValue){
        this.authority = roleValue;
    }


    @Override
    public String getAuthority() {
        return authority;
    }


    @JsonProperty("expired")
    public boolean expired(){
        return this.expireTime!=null&&System.currentTimeMillis()>expireTime.getTime();
    }

}
