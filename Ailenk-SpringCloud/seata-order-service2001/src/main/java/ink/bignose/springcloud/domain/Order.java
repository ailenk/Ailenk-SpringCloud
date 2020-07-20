package ink.bignose.springcloud.domain;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Order {
    private Long id;
    private Long userId;
    private Long productId;
    private Integer count;
    private BigDecimal money;
    /**
     * 订单状态：0，创建中；1，已完成
     */
    private Integer status;
}
