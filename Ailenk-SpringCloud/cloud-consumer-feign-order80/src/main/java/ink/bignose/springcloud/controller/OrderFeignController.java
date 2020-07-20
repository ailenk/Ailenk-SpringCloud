package ink.bignose.springcloud.controller;

import ink.bignose.springcloud.entities.CommonResult;
import ink.bignose.springcloud.entities.Payment;
import ink.bignose.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderFeignController {

    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping("/consumer/feign/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        return paymentFeignService.getPaymentById(id);
    }

    @GetMapping("/consumer/feign/payment/timeout")
    public String getPaymentById() {
        // openfeign-ribbon，客户端默认等待1秒钟
        return paymentFeignService.paymentFeignTimeout();
    }
}
