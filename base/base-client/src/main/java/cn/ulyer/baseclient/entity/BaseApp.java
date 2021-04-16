package cn.ulyer.baseclient.entity;

import cn.ulyer.common.model.AbstractBaseModel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author mybatis-plus generator
 * @since 2021-04-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("base_app")
public class BaseApp extends AbstractBaseModel {

    private static final long serialVersionUID = 1L;

    @TableId(value = "appId", type = IdType.ASSIGN_ID)
    private String appId;

    private String appName;

    private String appSecret;

    private String appType;

    private String appIcon;

    private String redirectUri;

    private String scope;

    private String grantTypes;

    private Integer tokenValidSeconds;

    private Integer refreshTokenValidSeconds;

    private Boolean autoApproval;

    private String jsonInformation;




}
