package cn.ulyer.auth.secure.phone;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "auth.phone")
@Component
@Data
public class PhoneProperties {

    /**
     * 登录地址
     */
    private String loginPath;


    /**
     * 验证码长度
     */
    private Integer codeLength;

    /**
     * 验证码有效期  单位秒
     */
    private Integer validTime;

    /**
     * 请求code 时长间隙 秒
     */
    private String requestGap;
    /**
     * 发送短信的模板  %code%  ...args
     */
    private String template;

}
