package ink.bignose.springcloud.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import ink.bignose.springcloud.entities.CommonResult;

public class CustomBlockHandler {

    public static CommonResult handlerException(BlockException exception) {
        return new CommonResult(444, "按客户自定义的 全局 异常处理----1");
    }

    public static CommonResult handlerException2(BlockException exception) {
        return new CommonResult(444, "按客户自定义的 全局 异常处理----2");
    }
}
