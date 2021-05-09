package cn.ulyer.gateway.locator;

import cn.ulyer.baseclient.entity.BaseResourceServer;
import cn.ulyer.common.binder.RouteBinding;
import cn.ulyer.common.event.RefreshRouteEvent;
import cn.ulyer.common.utils.SpringUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Slf4j
public class JdbcRouteLocator  {

    private ResourceLocator resourceLocator;

    private RouteDefinitionRepository routeDefinitionRepository;




    public JdbcRouteLocator (ResourceLocator resourceLocator, RouteDefinitionRepository repository){
        this.resourceLocator = resourceLocator;
        this.routeDefinitionRepository = repository;
    }






    public Mono<Void> refresh(){
        this.loadRoutes();
        SpringUtils.publishEvent(new RefreshRoutesEvent(this));
        return Mono.empty();
    }

    private Mono<Void> loadRoutes() {
        List<BaseResourceServer> resourceServers = resourceLocator.refreshResourceServer();
        resourceServers.forEach(baseResourceServer -> {
            RouteDefinition route = new RouteDefinition();
            route.setId(baseResourceServer.getServiceId());
            route.setUri(UriComponentsBuilder.fromUriString("lb://"+baseResourceServer.getServiceId()).build().toUri());
            List<PredicateDefinition> predicateDefinitions = new ArrayList<>();
            PredicateDefinition predicate = new PredicateDefinition();
            predicate.setName("Path");
            predicate.addArg("Path",baseResourceServer.getRouterMatch()+"/**");
            predicateDefinitions.add(predicate);


            route.setPredicates(predicateDefinitions);

            List<FilterDefinition> filters = new ArrayList<>();
            FilterDefinition filterDefinition = new FilterDefinition();
            filterDefinition.setName("StripPrefix");
            filterDefinition.addArg(NameUtils.GENERATED_NAME_PREFIX + "0", "1");
            filters.add(filterDefinition);
            route.setFilters(filters);
            this.routeDefinitionRepository.save(Mono.just(route)).subscribe();
        });
        return Mono.empty();
    }


    @StreamListener(value = RouteBinding.INPUT,condition = "headers['"+RefreshRouteEvent.FLAG_NAME+"']==true")
    public void onEvent(RefreshRouteEvent refreshRouteEvent){
        log.info("刷新网关");
        this.refresh();
    }








}
