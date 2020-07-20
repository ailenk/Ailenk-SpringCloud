package ink.bignose.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangsj
 * @date 2020/3/17 13:23
 */
@RestController
@EnableBinding(Sink.class)
@Slf4j
public class ReceiveMessageListenerController {

    @Value("${server.port}")
    private String serverPort;

    @StreamListener(Sink.INPUT)
    private void input(Message<String> message) {
        log.info("<<<<< 消费者1号 {} <<<<<<  {}", serverPort, message.getPayload());
    }
}
