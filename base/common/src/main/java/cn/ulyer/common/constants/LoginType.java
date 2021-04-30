package cn.ulyer.common.constants;

import java.util.Arrays;
import java.util.Optional;

public enum LoginType {

    /**
     * 表单提交
     */
    FORM,
    /**
     * 手机登录
     */
    PHONE,
    /**
     * 第三方登录
     */
    OAUTH2,

    INVALID;

    public static LoginType resolveType(String type){
        Optional<LoginType> loginType = Arrays.stream(values()).filter(v->v.name().equals(type)).findFirst();
        return loginType.orElse(INVALID);
    }
}
