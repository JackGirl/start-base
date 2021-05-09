package cn.ulyer.common.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshResourceEvent extends RefreshRouteEvent {

    public final static String FLAG_NAME = "resourceFlag";

    private Object data;

    private ActionType actionType;

    public RefreshResourceEvent(){
        super("");
    }

    public RefreshResourceEvent(Object data,ActionType actionType){
        super(data);
        this.actionType = actionType;
        this.data = data;
    }


    public enum ActionType{

        INSERT,
        UPDATE,
        /**
         * 刷新
         */
        RELOAD,
        DELETE
    }
}
