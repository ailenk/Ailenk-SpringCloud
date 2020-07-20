package ink.bignose.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "-------PaymentFallbackService fall back paymentInfo_OK, o(╥﹏╥)o";
    }

    @Override
    public String paymentInfo_TIMEOUT(Integer id) {
        return "-------PaymentFallbackService fall back paymentInfo_TIMEOUT, o(╥﹏╥)o";
    }
}
