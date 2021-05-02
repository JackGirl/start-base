package cn.ulyer.common.exception;

import org.springframework.security.core.AuthenticationException;

public class LoginTypeException extends AuthenticationException {
    public LoginTypeException(String msg) {
        super(msg);
    }

    public LoginTypeException(String msg, Throwable t) {
        super(msg, t);
    }
}
