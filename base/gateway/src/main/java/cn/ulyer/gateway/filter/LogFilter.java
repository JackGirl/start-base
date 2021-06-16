package cn.ulyer.gateway.filter;

import cn.ulyer.common.binder.LogOutput;
import cn.ulyer.common.model.GateWayLog;
import cn.ulyer.gateway.utils.WebFluxUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;


@Slf4j
@Component
public class LogFilter implements GlobalFilter, Ordered {


    @Autowired
    private LogOutput logOutput;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);

        return chain.filter(exchange).then(Mono.fromRunnable(()->{
            GateWayLog gateWayLog = GateWayLog.builder()
                    .ip(WebFluxUtils.getRemoteAddress(exchange))
                    .method(request.getMethodValue())
                    .path(request.getURI().getPath())
                    .requestTime(new Date())
                    .userAgent(request.getHeaders().toSingleValueMap().get(HttpHeaders.USER_AGENT))
                    .queryParam(JSON.toJSONString(request.getQueryParams()))
                    .build();
            if(route!=null){
                gateWayLog.setServiceId(route.getId());
            }
            gateWayLog.setRequestHeaders(request.getHeaders().toString());

            gateWayLog.setResponseHeader(response.getHeaders().toString());
            gateWayLog.setResponseStatus(response.getStatusCode().toString());
            gateWayLog.setResponseTime(new Date());
            logOutput.output().send(MessageBuilder.withPayload(gateWayLog).build());
            log.info("send one message {}",JSON.toJSON(gateWayLog));
        }));
    }


    @Override
    public int getOrder() {
        return -2;
    }


}
