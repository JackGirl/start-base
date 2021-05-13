package cn.ulyer.gateway.filter;

import cn.ulyer.common.model.GateWayLog;
import cn.ulyer.gateway.filter.decorator.ExchangeDecorator;
import cn.ulyer.gateway.utils.WebFluxUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class LogFilter implements GlobalFilter, Ordered {


    @Autowired
    private Environment environment;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ExchangeDecorator exchangeDecorator = new ExchangeDecorator(exchange);
        ServerHttpRequest request = exchangeDecorator.getRequest();
        ServerHttpResponse originResponse = exchange.getResponse();

        GateWayLog gateWayLog = GateWayLog.builder()
                .ip(WebFluxUtils.getRemoteAddress(exchange))
                .method(request.getMethodValue())
                .path(request.getURI().getPath())
                .requestTime(new Date())
                .params(JSON.toJSONString(request.getQueryParams()))
                .userAgent(request.getHeaders().toSingleValueMap().get(HttpHeaders.USER_AGENT))
                .instance(environment.getProperty("spring.application.name")+environment.getProperty("server.port"))
                .build();


        gateWayLog.setRequestHeaders(request.getHeaders().toString());
        request.getBody().subscribe(dataBuffer -> {
            gateWayLog.setRequestBody(dataBuffer.toString(StandardCharsets.UTF_8));;
            DataBufferUtils.release(dataBuffer);
        });
       ServerHttpResponse responseDecorator =  new ServerHttpResponseDecorator(originResponse){
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                        MediaType contentType = getHeaders().getContentType();
                        if(MediaType.APPLICATION_JSON.includes(contentType)){
                            Mono<Void> newMono = super.writeWith(
                                    DataBufferUtils.join(body)
                                            .doOnNext(dataBuffer -> {
                                                String respBody = dataBuffer.toString(StandardCharsets.UTF_8);
                                                gateWayLog.setResponseBody(respBody);
                                            })
                            );
                            return newMono;
                        }
                    return super.writeWith(body);
                }
                return super.writeWith(body);
            }
        };
        exchangeDecorator.response(responseDecorator);

        return chain.filter(exchangeDecorator).then(Mono.fromRunnable(()->{
            gateWayLog.setResponseHeader(responseDecorator.getHeaders().toString());
            gateWayLog.setResponseStatus(responseDecorator.getStatusCode().toString());
            gateWayLog.setResponseTime(new Date());
            log.info("{}",JSON.toJSON(gateWayLog));
        }));
    }


    @Override
    public int getOrder() {
        return -2;
    }


}
