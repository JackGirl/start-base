package cn.ulyer.baseapi.entity;

import cn.ulyer.common.model.AbstractBaseModel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    private Long appId;

    @NotBlank
    private String appName;

    private String appSecret;

    @NotBlank
    private String appType;

    private String appIcon;

    @NotBlank
    private String redirectUri;

    private String scope;

    @NotBlank
    private String grantTypes;

    @NotNull
    private Integer tokenValidSeconds;

    @NotNull
    private Integer refreshTokenValidSeconds;

    @NotNull
    private Integer autoApproval;

    private String jsonInformation;

    @NotNull
    private Integer status;
    /**
     * 是否内部的app   可有由这个字段决定是用用户权限 还是 client权限
     */
    @NotNull
    private Integer internal;




}
