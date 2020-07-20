package ink.bignose.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author zhangsj
 * @date 2020/3/17 9:34
 */
@SpringBootApplication
@EnableEurekaClient
public class StreamRabbitmqMain8801 {
    public static void main(String[] args) {
        SpringApplication.run(StreamRabbitmqMain8801.class, args);
    }
}
