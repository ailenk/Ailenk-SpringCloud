package ink.bignose.springcloud.service.impl;

import cn.hutool.core.util.IdUtil;
import ink.bignose.springcloud.service.IMessageProviderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;

/**
 * @author zhangsj
 * @date 2020/3/17 9:58
 */
@EnableBinding(Source.class) // 定义消息的推送管道
@Slf4j
public class MessageProviderImpl implements IMessageProviderService {

    @Resource
    private MessageChannel output;

    @Override
    public String send() {
        String serial = IdUtil.simpleUUID();
        Message<String> message = MessageBuilder.withPayload(serial).build();
        boolean result = output.send(message);
        log.info(">>>>> send message {} >>>>  {}, ", result, serial);
        log.info(">>>>> send message {} >>>>  {}, ", result, message);
        return serial;
    }
}
