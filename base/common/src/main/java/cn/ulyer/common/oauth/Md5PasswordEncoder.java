package cn.ulyer.common.oauth;

import cn.hutool.crypto.SecureUtil;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Md5PasswordEncoder implements PasswordEncoder {


    @Override
    public String encode(CharSequence charSequence) {
        return SecureUtil.md5((String) charSequence);
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return charSequence.equals(s);
    }




}
