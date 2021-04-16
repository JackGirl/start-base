package cn.ulyer.common.event;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

public class RemoteRefreshRouteEvent extends RemoteApplicationEvent {



    public RemoteRefreshRouteEvent(Object source, String originService, String destinationService) {
        super(source, originService, destinationService);
    }

    public RemoteRefreshRouteEvent(Object source, String originService) {
        super(source, originService);
    }
}
