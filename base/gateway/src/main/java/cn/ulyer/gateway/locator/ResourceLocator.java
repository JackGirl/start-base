package cn.ulyer.gateway.locator;

import cn.ulyer.baseclient.client.ResourceClient;
import cn.ulyer.baseclient.entity.BaseResource;
import cn.ulyer.baseclient.entity.BaseResourceServer;
import cn.ulyer.common.binder.RouteBinding;
import cn.ulyer.common.constants.SystemConstants;
import cn.ulyer.common.event.RefreshResourceEvent;
import cn.ulyer.common.event.RefreshRouteEvent;
import cn.ulyer.common.utils.R;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.ApplicationEvent;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j

public class ResourceLocator {

    private ResourceClient resourceClient;

    private JdbcTemplate jdbcTemplate;

    final static String QUERY = "select * from base_resource_server where status=" + SystemConstants.STATUS_VALID;


    /**
     * key全路由
     */
    private Map<String, BaseResource>       routerResourceMap = new ConcurrentHashMap<>(256);
    /**
     * 路由服务
     */
    private Map<String, BaseResourceServer> serverMap         = new ConcurrentHashMap<>();

    public ResourceLocator(ResourceClient resourceClient, JdbcTemplate jdbcTemplate) {
        this.resourceClient = resourceClient;
        this.jdbcTemplate = jdbcTemplate;
    }


    @StreamListener(value = RouteBinding.INPUT, condition = "headers['" + RefreshResourceEvent.FLAG_NAME + "']==true")
    public void onRefreshResource(RefreshResourceEvent resourceEvent) {
        BaseResource resource = JSONObject.toJavaObject((JSON) JSON.toJSON(resourceEvent.getData()), BaseResource.class);
        try {
            switch (resourceEvent.getActionType()) {
                case UPDATE:
                    Optional<Map.Entry<String, BaseResource>> entry = routerResourceMap.entrySet().stream().filter(v -> v.getValue().getResourceId().equals(resource.getResourceId())).findFirst();
                    if(!entry.isPresent()){
                      break;
                    }
                    routerResourceMap.remove(entry.get().getKey());
                case INSERT:
                    routerResourceMap.put(serverMap.get(resource.getServiceId()).getRouterMatch()+resource.getPath(),resource);
                    break;
                case DELETE:
                    routerResourceMap.remove(serverMap.get(resource.getServiceId()).getRouterMatch()+resource.getPath());
                    break;
                case RELOAD:
                    refreshResources();
                    break;
                default:
                    return;
            }
        } catch (Exception e) {
            log.error("resource刷新异常:{}", e);
        }

    }

    public List<BaseResourceServer> refreshResourceServer() {
        List<BaseResourceServer> resourceServers = this.jdbcTemplate.query(QUERY, new BeanPropertyRowMapper<>(BaseResourceServer.class));
        if (resourceServers == null) {
            resourceServers = new ArrayList<>();
        }
        serverMap.clear();
        resourceServers.forEach(resourceServer -> serverMap.put(resourceServer.getServiceId(), resourceServer));
        refreshResources();
        return resourceServers;
    }


    public void refreshResources() {
        routerResourceMap.clear();

        R<List<BaseResource>> result = resourceClient.loadSystemResources();
        List<BaseResource> resources = result.getData();
        resources.forEach(resource -> {
            BaseResourceServer resourceServer = serverMap.get(resource.getServiceId());
            if (resourceServer == null) {
                return;
            }
            routerResourceMap.put(resourceServer.getRouterMatch() + resource.getPath(), resource);
        });
    }

    public Map<String, BaseResource> getRouterResourceMap() {
        return routerResourceMap;
    }

    public Map<String, BaseResourceServer> getServerMap() {
        return serverMap;
    }
}
