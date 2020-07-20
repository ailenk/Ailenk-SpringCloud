package ink.bignose.springcloud.controller;

import ink.bignose.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "paymentGlobalFallBack")
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String ok(@PathVariable("id") Integer id) {
        return paymentHystrixService.paymentInfo_OK(id);
    }


    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
//    @HystrixCommand(fallbackMethod = "paymentInfo_TIMEOUTHandler", commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "15000")
//    })
    @HystrixCommand
    public String timeout(@PathVariable("id") Integer id) {
        return paymentHystrixService.paymentInfo_TIMEOUT(id);
    }

    public String paymentInfo_TIMEOUTHandler(Integer id) {
        return "80服务超时了o(╥﹏╥)o";
    }

    // 下面是全局fallback方法
    public String paymentGlobalFallBack() {
        return "全局异常信息处理，请稍后再试o(╥﹏╥)o";
    }
}
