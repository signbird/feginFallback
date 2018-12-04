package org.bqf.fallback;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@EnableEurekaClient
//@EnableFeignClients(basePackages = {"org.bqf"})
@SpringBootApplication
@ComponentScan(basePackages = {"org.bqf"})
@MapperScan("org.bqf.fallback.dao")
public class FallbackApplication {

    /**
     * 调用其他服务的rest模板
     */
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
	public static void main(String[] args) {
		SpringApplication.run(FallbackApplication.class, args);
	}
}
