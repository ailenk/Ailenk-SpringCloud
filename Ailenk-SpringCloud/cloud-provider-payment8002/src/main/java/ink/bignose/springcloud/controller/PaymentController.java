package ink.bignose.springcloud.controller;

import ink.bignose.springcloud.entities.CommonResult;
import ink.bignose.springcloud.entities.Payment;
import ink.bignose.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping("/payment")
public class PaymentController {

    @Resource
    public PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping("/create")
    public CommonResult<Integer> create(@RequestBody Payment payment) {
        log.info("机器：[{}]  新增支付信息{}", serverPort, payment);
        int i = paymentService.create(payment);
        log.info("机器：[{}]  新增支付信息结果{}", serverPort, i > 0 ? "成功" : "失败");
        if (i > 0) {
            return new CommonResult<>(200, "新增支付信息成功", serverPort, i);
        } else {
            return new CommonResult<>(444, "新增支付信息失败");
        }
    }

    @GetMapping("/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        log.info("机器：[{}]  查询支付信息{}", serverPort, id);
        System.out.println("dafhakdhfalsdfa  ");
        Payment payment = paymentService.getPaymentById(id);
        log.info("机器：[{}]  查询支付信息结果{}", serverPort, payment);
        if (payment != null) {
            return new CommonResult<>(200, "查询成功", serverPort, payment);
        } else {
            return new CommonResult<>(444, "查询失败");
        }
    }

    @GetMapping("/lb")
    public String lb() {
        return serverPort;
    }

    @GetMapping("/discovery")
    public Object discovery() {
        List<String> services = this.discoveryClient.getServices();
        for (String service : services) {
            log.info("service >>> {}", service);
        }

        List<ServiceInstance> instances = this.discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info("host .... {}", instance.getHost());
            log.info("port .... {}", instance.getPort());
            log.info("url .... {}", instance.getUri());
        }
        return this.discoveryClient;
    }

    @GetMapping("/feign/timeout")
    public String paymentFeignTimeout(){
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }
}
