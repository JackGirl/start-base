package cn.ulyer.auth.secure.phone;

import lombok.Data;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


/**
 * 手机token
 */
@Getter
public class PhoneAuthenticationToken extends AbstractAuthenticationToken {

    private String phone;

    private String code;


    public PhoneAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    public PhoneAuthenticationToken(String phone,String code){
        super(null);
        this.code = code;
        this.phone = phone;
    }

    @Override
    public Object getCredentials() {
        return code;
    }

    @Override
    public String getName() {
        return phone;
    }

    @Override
    public Object getPrincipal() {
        return phone;
    }
}
