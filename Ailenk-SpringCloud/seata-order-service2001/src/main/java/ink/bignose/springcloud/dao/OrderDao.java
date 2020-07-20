package ink.bignose.springcloud.dao;

import ink.bignose.springcloud.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderDao {

    /**
     * 新建订单
     */
    void create(Order order);

    /**
     * 修改订单状态，从0改为1
     * @param userId 用户id
     * @param status 订单状态
     */
    void update(@Param("userId") Long userId, @Param("status") Integer status);
}
