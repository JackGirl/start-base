package cn.ulyer.gateway.locator;

import cn.ulyer.baseclient.entity.BaseResourceServer;
import cn.ulyer.common.constants.SystemConstants;
import cn.ulyer.common.event.RemoteRefreshRouteEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Slf4j
public class JdbcRouteLocator  implements ApplicationEventPublisherAware , ApplicationListener<RemoteRefreshRouteEvent> {

    private JdbcTemplate jdbcTemplate;

    private RouteDefinitionRepository routeDefinitionRepository;

    private ApplicationEventPublisher eventPublisher;

    final static String QUERY = "select * from base_resource_server where status="+ SystemConstants.STATUS_VALID;


    public JdbcRouteLocator (JdbcTemplate jdbcTemplate, RouteDefinitionRepository repository){
        this.jdbcTemplate = jdbcTemplate;
        this.routeDefinitionRepository = repository;
    }



    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }


    public Mono<Void> refresh(){
        this.loadRoutes();
        this.eventPublisher.publishEvent(new RefreshRoutesEvent(this));
        return Mono.empty();
    }

    private Mono<Void> loadRoutes() {
        List<BaseResourceServer> resourceServers = this.jdbcTemplate.query(QUERY,new BeanPropertyRowMapper<>(BaseResourceServer.class));
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


    @Override
    public void onApplicationEvent(RemoteRefreshRouteEvent remoteRefreshRouteEvent) {
       log.info("remoteRefreshRouteEvent :{}",remoteRefreshRouteEvent);
       refresh();
       log.info("refresh end;");
    }
}
