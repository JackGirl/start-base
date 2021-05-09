package cn.ulyer.common.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;


@Setter
@Getter
public class RefreshRouteEvent extends ApplicationEvent {

    public final static String FLAG_NAME = "routeFlag";


    private Object data;

    public RefreshRouteEvent(){
        super("");
    }

    public RefreshRouteEvent(Object data){
        super(data);
        this.data = data;
    }


}
