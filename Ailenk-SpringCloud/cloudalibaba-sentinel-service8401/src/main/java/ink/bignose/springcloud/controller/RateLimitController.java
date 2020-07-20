package ink.bignose.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import ink.bignose.springcloud.entities.CommonResult;
import ink.bignose.springcloud.entities.Payment;
import ink.bignose.springcloud.handler.CustomBlockHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {

    @Value("${server.port}")
    private String serverPort;


    @GetMapping("/byResource")
    @SentinelResource(value = "byResource", blockHandler = "handleException")
    public CommonResult byResource() {
        return new CommonResult(200, "按照资源名称限流测试 OK", serverPort, new Payment(2020L, "serial001"));
    }

    public CommonResult handleException(BlockException e) {
        return new CommonResult(444, e.getClass().getCanonicalName() + "\t 服务不可用");
    }


    @GetMapping("/rateLimit/byUrl")
    @SentinelResource(value = "byUrl")
    public CommonResult byUrl() {
        return new CommonResult(200, "按URL限流测试 OK", serverPort,
                new Payment(2020l, "serial-002"));
    }

    @GetMapping("/rateLimit/customBlockHandler")
    @SentinelResource(value = "customBlockHandler",
            blockHandlerClass = CustomBlockHandler.class,
            blockHandler = "handlerException2")
    public CommonResult customBlockHandler() {
        return new CommonResult(200, "按客户自定义限流测试 OK", serverPort,
                new Payment(2020l, "serial-003"));
    }
}
