package cn.ulyer.gateway.utils;

import cn.ulyer.common.utils.WebUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

public class WebFluxUtils extends WebUtils {


    public static Mono<Void> writeJson(ServerHttpResponse response, String message){
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        DataBufferFactory dataBufferFactory = response.bufferFactory();
        DataBuffer buffer = dataBufferFactory.wrap(message.getBytes(Charset.defaultCharset()));
        // 保存日志
        return response.writeWith(Mono.just(buffer)).doOnError((error) -> {
            DataBufferUtils.release(buffer);
        });
    }

}
