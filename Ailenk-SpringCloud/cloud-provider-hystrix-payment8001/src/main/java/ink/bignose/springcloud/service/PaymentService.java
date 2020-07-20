package ink.bignose.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    /**
     * 正常访问，肯定OK
     *
     * @param id
     * @return
     */
    public String paymentInfo_OK(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + "    paymentInfo_OK, id=" + id + "   O(∩_∩)O哈哈~";
    }


    /**
     * 超时报错
     *
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_TIMEOUTHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public String paymentInfo_TIMEOUT(Integer id) {
        int timeNum = 10;
        try {
            TimeUnit.SECONDS.sleep(timeNum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + "    paymentInfo_TIMEOUT, id=" + id + "  耗时" + timeNum + "秒钟";
    }

    public String paymentInfo_TIMEOUTHandler(Integer id) {
        return "8001服务超时了o(╥﹏╥)o";
    }


    // ================= 服务熔断 ==================
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), // 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), // 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), // 时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60") // 失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(Integer id) {
        if (id < 0) {
            throw new RuntimeException("******** id 不能为负数 *********");
        }
        String simpleUUID = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t" + "调用成功， 流水号: " + simpleUUID;
    }

    public String paymentCircuitBreaker_fallback(Integer id) {
        return "******** id 不能为负数 请稍后再试 o(╥﹏╥)o *********";
    }
}
