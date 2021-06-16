package cn.ulyer.baseserver;

import cn.ulyer.common.binder.LogInput;
import cn.ulyer.common.binder.RouteOutput;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication(scanBasePackages = "cn.ulyer")
@MapperScan(basePackages = "cn.ulyer.baseserver.mapper")
@EnableBinding({RouteOutput.class, LogInput.class})
@Slf4j
public class BaseServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseServerApplication.class, args);
    }



}
