package ink.bignose.springcloud.service.impl;

import ink.bignose.springcloud.dao.OrderDao;
import ink.bignose.springcloud.domain.Order;
import ink.bignose.springcloud.service.AccountService;
import ink.bignose.springcloud.service.OrderService;
import ink.bignose.springcloud.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;

    @Resource
    private StorageService storageService;

    @Resource
    private AccountService accountService;

    /**
     * 创建订单 -> 调用库存服务扣减库存 -> 调用账户服务扣减账户余额 -> 修改订单状态
     */
    @Override
    public void create(Order order) {
        log.info(" ------ >>>>> 开始新建订单 >>>>>>> ------");
        orderDao.create(order);

        log.info(" ------ >>>>> 订单微服务开始调用【库存】，做扣减Count >>>>>>> -----");
        storageService.decrease(order.getProductId(), order.getCount());
        log.info(" ------ >>>>> 订单微服务开始调用【库存】，做扣减Count 结束 >>>>>>> -----");

        log.info(" ------ >>>>> 订单微服务开始调用【账户】，做扣减Money >>>>>>> -----");
        accountService.decrease(order.getUserId(), order.getMoney());
        log.info(" ------ >>>>> 订单微服务开始调用【账户】，做扣减Money 结束 >>>>>>> -----");

        log.info(" ------ >>>>> 开始修改订单开始 >>>>>>> ------");
        orderDao.update(order.getUserId(), 0);
        log.info(" ------ >>>>> 开始修改订单结束 >>>>>>> ------");

        log.info(" ------ >>>>> 下订单结束了，O(∩_∩)O哈哈~ >>>>>>> ------");
    }
}
