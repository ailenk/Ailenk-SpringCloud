package ink.bignose.springcloud.service;

import ink.bignose.springcloud.entities.CommonResult;
import ink.bignose.springcloud.entities.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallBackService implements PaymentService {
    @Override
    public CommonResult<Payment> paymentSQL(Long id) {
        return new CommonResult<>(444444,
                "服务降级返回--- PaymentFallBackService",
                null,
                new Payment(id, "serial error"));
    }
}
