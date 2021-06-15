package cn.ulyer.activiti;

import cn.ulyer.common.config.MybatisPlusConfig;
import cn.ulyer.common.config.WebConfig;
import cn.ulyer.common.oauth.GlobalExceptionHandler;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication(
        exclude ={ SecurityAutoConfiguration.class})
@EnableDiscoveryClient
@Import({WebConfig.class, GlobalExceptionHandler.class, MybatisPlusConfig.class})
@MapperScan(basePackages = "cn.ulyer.activiti.mapper")
public class ActivitiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiApplication.class, args);
    }

}
