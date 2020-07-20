package ink.bignose.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import ink.bignose.springcloud.entities.CommonResult;
import ink.bignose.springcloud.entities.Payment;
import ink.bignose.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class CirClieBreakerController {

    @Value("${service-url.nacos-user-service}")
    private String serverURL;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/fallback/{id}")
//    @SentinelResource(value = "fallback") // 没有配置
//    @SentinelResource(value = "fallback", fallback = "handlerFallback") // fallback只负责业务异常
//    @SentinelResource(value = "fallback", blockHandler = "blockHandler") // blockHandler只负责Sentinel控制台配置违规
    @SentinelResource(value = "fallback", blockHandler = "blockHandler", fallback = "handlerFallback")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id) {
        CommonResult<Payment> result = restTemplate.getForObject(serverURL + "/paymentSQL/" + id, CommonResult.class);

        if (id == 4) {
            throw new IllegalArgumentException("IllegalArgumentException, 非法参数异常。。。。");
        } else if (result.getData() == null) {
            throw new NullPointerException("NullPointerException，该id没有对应的记录，空指针异常");
        }

        return result;
    }

    /**
     * 测试blockHandler
     */
    public CommonResult blockHandler(@PathVariable("id") Long id, BlockException e) {
        Payment payment = new Payment(id, null);
        return new CommonResult(445,
                "blockHandler-sentinel限流，无此流水:" + e.getMessage(),
                serverPort,
                payment);
    }

    /**
     * 测试fallback
     */
    public CommonResult handlerFallback(@PathVariable("id") Long id, Throwable e) {
        Payment payment = new Payment(id, null);
        return new CommonResult(444,
                "兜底异常handlerFallback， 异常信息:" + e.getMessage(),
                serverPort,
                payment);
    }

    // ==================== openfeign ==========
    @Resource
    private PaymentService paymentService;

    @GetMapping("/consumer/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id){
        CommonResult<Payment> result = paymentService.paymentSQL(id);
        return result;
    }
}
