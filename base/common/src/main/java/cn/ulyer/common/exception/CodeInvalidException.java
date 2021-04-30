package cn.ulyer.common.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * 验证码无效
 */
public class CodeInvalidException extends AuthenticationException {

    public CodeInvalidException(String msg) {
        super(msg);
    }
}
