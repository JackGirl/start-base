package cn.ulyer.common.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class WebUtils extends org.springframework.web.util.WebUtils {

    public static HttpServletRequest getRequest(){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes.getRequest();
    }

    /**
     * Describe: 获取 HttpServletResponse 对象
     * Param null
     * Return HttpServletResponse
     * */
    public static HttpServletResponse getResponse(){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes.getResponse();
    }



    /**
     * 客户端返回字符串
     *
     * @param response
     * @param string
     * @return
     */
    public static void writeJson(HttpServletResponse response, String string) {
        try {
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
        }
    }

    /**
     * Describe: 获取 HttpServletSession 对象
     * Param null
     * Return HttpServletSession
     * */
    public static HttpSession getSession(){
        return getRequest().getSession();
    }

    /**
     * Describe: 判断是否为 Ajax 请求
     * Param null
     * Return HttpServletSession
     * */
    public static Boolean isAjax(HttpServletRequest request){
        String requestType = request.getHeader("X-Requested-With");
        if("XMLHttpRequest".equals(requestType)){
            return true;
        }else{
            return false;
        }
    }


    /**
     * 获取查询参数
     * */
    public static String getQueryParam(){
        return getRequest().getQueryString();
    }

    /**
     * 获取请求地址
     * */
    public static String getRequestURI() {
        return getRequest().getRequestURI();
    }

    /**
     * 获取客户端地址
     * */
    public static String getRemoteHost(){
        String remoteHost = getRequest().getRemoteHost();
        if(remoteHost.equals("0:0:0:0:0:0:0:1")){
            remoteHost = "127.0.0.1";
        }
        return remoteHost ;
    }

    /**
     * 获取当前请求方法
     * */
    public static String getMethod(){
        return getRequest().getMethod();
    }

    /**
     * 获取请求头
     * */
    public static String getHeader(String name){
        return getRequest().getHeader(name);
    }

    /**
     * 获取 UserAgent
     * */
    public static String getAgent(){
        return getHeader("User-Agent");
    }



    /**
     * 获取浏览器类型
     * */
    public static String getOperatorSystem(){
        String userAgent = getAgent();
        if (userAgent.toLowerCase().contains("windows" )) {
            return "Windows";
        } else if (userAgent.toLowerCase().contains("mac" )) {
            return "Mac";
        } else if (userAgent.toLowerCase().contains("x11" )) {
            return "Unix";
        } else if (userAgent.toLowerCase().contains("android" )) {
            return "Android";
        } else if (userAgent.toLowerCase().contains("iphone" )) {
            return "IPhone";
        } else {
            return "UnKnown, More-Info: " + userAgent;
        }
    }




}
