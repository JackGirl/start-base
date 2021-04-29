package cn.ulyer.common.oauth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Data
public class Oauth2User implements UserDetails {

    private Long id;

    private String account;

    @JsonIgnore
    private String password;

    private String username;

    private String orgId;

    private Date createTime;

    private Date updateTime;

    private String remark;

    private String avatar;

    private Integer accountLocked;

    private Integer accountExpired;

    private Integer passwordExpired;

    private Integer enable;

    private Set<Oauth2Authority> authorities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.account;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountExpired==0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountLocked==0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return passwordExpired==0;
    }

    @Override
    public boolean isEnabled() {
        return enable==1;
    }
}
