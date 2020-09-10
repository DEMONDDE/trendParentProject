package cn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author 胡建德
 */
@SpringBootApplication
@EnableEurekaClient
public class IndexGatherStoreService {

    public static void main(String[] args) {
        SpringApplication.run(IndexGatherStoreService.class,args);
    }
}
