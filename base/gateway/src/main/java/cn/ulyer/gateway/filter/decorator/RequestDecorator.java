package cn.ulyer.gateway.filter.decorator;

import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.io.InputStream;

import static reactor.core.scheduler.Schedulers.single;

@Setter
@Slf4j
@Getter
public class RequestDecorator extends ServerHttpRequestDecorator {

    private boolean selfBody;

    private byte [] bytes;

    private ServerHttpRequest request;

    public RequestDecorator(ServerHttpRequest delegate) {
        super(delegate);
        this.request = delegate;
    }

    @Override
    public Flux<DataBuffer> getBody() {
        if(!selfBody){
            Flux<DataBuffer> flux = request.getBody();
            return   flux.map(dataBuffer -> {
                            InputStream buffer = dataBuffer.asInputStream();
                         try {
                             bytes = IOUtils.toByteArray(buffer);
                         } catch (IOException e) {
                             e.printStackTrace();
                         }
                         if(bytes==null){
                             bytes = new byte[0];
                            }
                            DataBufferUtils.release(dataBuffer);
                            NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(new UnpooledByteBufAllocator(false));
                            DataBuffer bf = nettyDataBufferFactory.wrap(bytes);
                            selfBody = true;
                            return bf;
                    });
        }
        return Flux.just(new NettyDataBufferFactory(new UnpooledByteBufAllocator(false)).wrap(bytes));
    }





}
