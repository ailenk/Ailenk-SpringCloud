package ink.bignose.springcloud.controller;

import ink.bignose.springcloud.service.IMessageProviderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhangsj
 * @date 2020/3/17 10:19
 */
@RestController
public class SendMessageController {

    @Resource
    private IMessageProviderService messageProviderService;

    @GetMapping("/sendMessage")
    public String sendMessage(){
        return messageProviderService.send();
    }
}
