package cn.ulyer.gateway.filter.decorator;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebExchangeDecorator;

public class ExchangeDecorator extends ServerWebExchangeDecorator {


    private final RequestDecorator request;

    private final ServerWebExchange serverWebExchange;

    private  ServerHttpResponse serverHttpResponse;

    public ExchangeDecorator(ServerWebExchange delegate) {
        super(delegate);
        this.serverWebExchange = delegate;
        this.request = new RequestDecorator(serverWebExchange.getRequest());
    }

    @Override
    public ServerHttpRequest getRequest() {
        return this.request;
    }


    public void response(ServerHttpResponse response){
        this.serverHttpResponse = response;
    }

    @Override
    public ServerHttpResponse getResponse() {
        return this.serverHttpResponse;
    }


    @Override
    public ServerWebExchange getDelegate() {
        return this.serverWebExchange;
    }
}
