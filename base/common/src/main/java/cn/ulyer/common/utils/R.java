package cn.ulyer.common.utils;

import cn.ulyer.common.constants.ErrorCode;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class R<T> implements Serializable {

    private Integer code;

    private String message;

    private String requestPath;

    private T data;


    public static R success(){
        return new R<>().setMessage(ErrorCode.OK.getMessage()).setCode(ErrorCode.OK.getCode());
    }

    public static R success(String message){
        return new R<>().setMessage(message).setCode(ErrorCode.OK.getCode());
    }

    public static R fail(){
        return new R<>().setMessage(ErrorCode.FAIL.getMessage()).setCode(ErrorCode.FAIL.getCode());
    }

    public static R fail(String message){
        return new R<>().setMessage(message).setCode(ErrorCode.FAIL.getCode());
    }

    public static R fail(ErrorCode errorCode){
        return new R<>().setMessage(errorCode.getMessage()).setCode(errorCode.getCode());
    }







}
