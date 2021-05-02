package cn.ulyer.baseclient.dto;

import cn.ulyer.common.model.AbstractBaseModel;
import cn.ulyer.common.oauth.Oauth2Authority;
import lombok.Data;

import java.util.Set;

@Data
public class LoginUser extends AbstractBaseModel {

    private String id;

    private String account;

    private String password;

    private String username;

    private String orgId;

    private String remark;

    private String avatar;

    private Integer accountLocked;

    private Integer accountExpired;

    private Integer passwordExpired;

    private Integer enable;

    private Set<Oauth2Authority> authorities;


}
