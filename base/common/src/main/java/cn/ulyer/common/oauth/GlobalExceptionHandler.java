package cn.ulyer.common.oauth;

import cn.ulyer.common.constants.ErrorCode;
import cn.ulyer.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
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
        R r = resolveException(ex,request.getRequestURI());
        response.setStatus(r.getHttpStatus().value());
        log.error("error for request  system error :{}", ExceptionUtils.getStackTrace(ex));
        return r;
    }



    public static R resolveException(Exception ex, String path) {
        ErrorCode code;
        String message = ex.getMessage();
        String className = ex.getClass().getName().substring(ex.getClass().getName().lastIndexOf(".")+1);
        HttpStatus httpStatus ;
        switch (className){
            case "Oauth2AuthenticationException":
            case "AuthenticationException":
            case "AuthenticationCredentialsNotFoundException":
                code = ErrorCode.UNAUTHORIZED;
                httpStatus = HttpStatus.UNAUTHORIZED;
                break;
            case "NoSuchClientException":
            case"InvalidClientException":
                code = ErrorCode.INVALID_CLIENT;
                httpStatus = HttpStatus.UNAUTHORIZED;
                break;
            case"UsernameNotFoundException":
                code = ErrorCode.USERNAME_NOT_FOUND;
                httpStatus = HttpStatus.UNAUTHORIZED;
                break;
            case"InvalidGrantException":
                code = ErrorCode.INVALID_GRANT;
                httpStatus = HttpStatus.UNAUTHORIZED;
                break;
            case "BadCredentialsException":
                code = ErrorCode.BAD_CREDENTIALS;
                httpStatus = HttpStatus.UNAUTHORIZED;
                break;
            case"AccountExpiredException":
                code = ErrorCode.ACCOUNT_EXPIRED;
                httpStatus = HttpStatus.UNAUTHORIZED;
                break;
            case"LockedException":
                code = ErrorCode.ACCOUNT_LOCKED;
                httpStatus = HttpStatus.UNAUTHORIZED;
                break;
            case"DisabledException":
                code = ErrorCode.ACCOUNT_DISABLED;
                httpStatus = HttpStatus.UNAUTHORIZED;
                break;
            case"CredentialsExpiredException":
                code = ErrorCode.CREDENTIALS_EXPIRED;
                httpStatus = HttpStatus.UNAUTHORIZED;
                break;
            case"UnauthorizedClientException":
                code = ErrorCode.UNAUTHORIZED_CLIENT;
                httpStatus = HttpStatus.UNAUTHORIZED;
                break;
            case"InvalidScopeException":
                code = ErrorCode.INVALID_SCOPE;
                httpStatus = HttpStatus.UNAUTHORIZED;
                break;
            case"InvalidTokenException":
                code = ErrorCode.INVALID_TOKEN;
                httpStatus = HttpStatus.UNAUTHORIZED;
                break;
            case"RedirectMismatchException":
                code = ErrorCode.REDIRECT_URI_MISMATCH;
                httpStatus = HttpStatus.UNAUTHORIZED;
                break;
            case"UnsupportedGrantTypeException":
                code = ErrorCode.UNSUPPORTED_GRANT_TYPE;
                httpStatus = HttpStatus.UNAUTHORIZED;
                break;
            case"UnsupportedResponseTypeException":
                code = ErrorCode.UNSUPPORTED_RESPONSE_TYPE;
                httpStatus = HttpStatus.UNAUTHORIZED;
                break;
            case"UserDeniedAuthorizationException":
                code = ErrorCode.ACCESS_DENIED_AUTHORITY_EXPIRED;
                httpStatus = HttpStatus.FORBIDDEN;
                break;
            case"AccessDeniedException":
                code = ErrorCode.ACCESS_DENIED;
                httpStatus = HttpStatus.FORBIDDEN;
                break;
            case"MissingServletRequestParameterException":
            case"MethodArgumentNotValidException":
            case"IllegalArgumentException":
                code = ErrorCode.BAD_REQUEST;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case"NoHandlerFoundException":
                code=ErrorCode.NOT_FOUND;
                httpStatus = HttpStatus.NOT_FOUND;
                break;
            case"HttpRequestMethodNotSupportedException":
            case"MethodNotAllowedException":
                code=ErrorCode.METHOD_NOT_ALLOWED;
                httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
                break;
            case"ServiceUnavailableException":
                code=ErrorCode.SERVICE_UNAVAILABLE;
                httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
                break;
            case"TimeoutException":
                code=ErrorCode.GATEWAY_TIMEOUT;
                httpStatus = HttpStatus.GATEWAY_TIMEOUT;
                break;
            default:
                code = ErrorCode.ERROR;
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return R.fail(code).setMessage(message).setRequestPath(path).setHttpStatus(httpStatus);
    }




}
