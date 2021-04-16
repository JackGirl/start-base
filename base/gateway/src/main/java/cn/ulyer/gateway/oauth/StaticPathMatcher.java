package cn.ulyer.gateway.oauth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;

import java.util.Set;


@Setter
@Getter
public class StaticPathMatcher {


    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private Set<String> permitAll ;

    public StaticPathMatcher(){

    }


    public boolean permit(ServerWebExchange exchange){
        String url = exchange.getRequest().getURI().getPath();
        boolean permit = permit(url);
        return permit;
    }


    private boolean permit(String url){
         return permitAll.stream().filter(permitUrl->antPathMatcher.match(permitUrl,url)).findFirst().isPresent();
    }

}
