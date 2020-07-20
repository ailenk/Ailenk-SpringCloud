package ink.bignose.springcloud.entities;


import lombok.*;


/**
 * 支付实体类
 *
 * @author zhangsj
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private Long id;
    private String serial;
}
