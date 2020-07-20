package ink.bignose.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangsj
 * @date 2020/3/18 16:08
 */
@RestController
@Slf4j
public class FlowLimitController {
    @GetMapping("/testA")
    public String testA() {
        return ">>>>>>> test A >>>>";
    }

    @GetMapping("/testB")
    public String testB() {
        return ">>>>>>> test B >>>>";
    }

    @GetMapping("/testD")
    public String testD() {
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        int age = 10 / 0;

        log.info("testD 异常比例");
        return "--------- testD";
    }

    @GetMapping("/testE")
    public String testE() {
        log.info("testE 测试异常数量");
        int aget = 10 / 0;
        return "-----testE 测试异常数量";
    }

    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey", blockHandler = "deal_testHotKey")
    public String testHotKey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2) {
        return "-----testHotKey";
    }

    public String deal_testHotKey(String p1, String p2, BlockException e) {
        return "---------deal_testHotKey o(╥﹏╥)o";
    }
}
