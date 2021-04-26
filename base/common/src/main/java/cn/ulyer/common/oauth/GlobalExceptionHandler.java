package cn.ulyer.common.oauth;

import cn.ulyer.common.constants.ErrorCode;
import cn.ulyer.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一异常处理器
 *
 * @author LYD
 * @date 2017/7/3
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    /**
     * 统一异常处理
     * AuthenticationException
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler({OAuth2AuthenticationException.class,AuthenticationException.class})
    public static R authenticationException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        R r = R.fail().setMessage(ex.getMessage()).setRequestPath(request.getRequestURI()).setCode(ErrorCode.UNAUTHORIZED.getCode());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return r;
    }

    /**
     * OAuth2Exception
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler({ InvalidTokenException.class})
    public static R oauth2Exception(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        R r = R.fail().setMessage(ex.getMessage()).setRequestPath(request.getRequestURI()).setCode(ErrorCode.UNAUTHORIZED.getCode());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return r;
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public static R argumentEx(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        R r = R.fail().setMessage(ex.getMessage()).setRequestPath(request.getRequestURI()).setCode(ErrorCode.BAD_REQUEST.getCode());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return r;
    }



    /**
     * 其他异常
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler({Exception.class})
    public static R exception(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        R r = R.fail().setMessage(ex.getMessage()).setRequestPath(request.getRequestURI()).setCode(ErrorCode.ERROR.getCode());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return r;
    }



    public static R resolveException(Exception ex, String path) {
        ErrorCode code;
        String message = ex.getMessage();
        String className = ex.getClass().getName().substring(ex.getClass().getName().lastIndexOf(".")+1);
        switch (className){
            case "Oauth2AuthenticationException":
            case "AuthenticationException":
                code = ErrorCode.UNAUTHORIZED;
                break;
            case "NoSuchClientException":
            case"InvalidClientException":
                code = ErrorCode.INVALID_CLIENT;
                break;
            case"UsernameNotFoundException":
                code = ErrorCode.USERNAME_NOT_FOUND;
                break;
            case"InvalidGrantException":
                code = ErrorCode.INVALID_GRANT;
                break;
            case "BadCredentialsException":
                code = ErrorCode.BAD_CREDENTIALS;
                break;
            case"AccountExpiredException":
                code = ErrorCode.ACCOUNT_EXPIRED;
                break;
            case"LockedException":
                code = ErrorCode.ACCOUNT_LOCKED;
                break;
            case"DisabledException":
                code = ErrorCode.ACCOUNT_DISABLED;
                break;
            case"CredentialsExpiredException":
                code = ErrorCode.CREDENTIALS_EXPIRED;
                break;
            case"UnauthorizedClientException":
                code = ErrorCode.UNAUTHORIZED_CLIENT;
                break;
            case"InvalidScopeException":
                code = ErrorCode.INVALID_SCOPE;
                break;
            case"InvalidTokenException":
                code = ErrorCode.INVALID_TOKEN;
                break;
            case"RedirectMismatchException":
                code = ErrorCode.REDIRECT_URI_MISMATCH;
                break;
            case"UnsupportedGrantTypeException":
                code = ErrorCode.UNSUPPORTED_GRANT_TYPE;
                break;
            case"UnsupportedResponseTypeException":
                code = ErrorCode.UNSUPPORTED_RESPONSE_TYPE;
                break;
            case"UserDeniedAuthorizationException":
                code = ErrorCode.ACCESS_DENIED_AUTHORITY_EXPIRED;
                break;
            case"AccessDeniedException":
                code = ErrorCode.ACCESS_DENIED;
                break;
            case"MissingServletRequestParameterException":
            case"MethodArgumentNotValidException":
            case"IllegalArgumentException":
                code = ErrorCode.BAD_REQUEST;
                break;
            case"NoHandlerFoundException":
                code=ErrorCode.NOT_FOUND;
                break;
            case"HttpRequestMethodNotSupportedException":
            case"MethodNotAllowedException":
                code=ErrorCode.METHOD_NOT_ALLOWED;
                break;
            default:
                code = ErrorCode.ERROR;
        }

        return R.fail(code).setMessage(message).setRequestPath(path);
    }




}
