package cn.ulyer.baseclient.dto;

import cn.ulyer.baseclient.entity.BaseApp;
import cn.ulyer.common.oauth.Oauth2Authority;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class App extends BaseApp {

    private Set<Oauth2Authority> authorities;

}
