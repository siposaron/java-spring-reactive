package eu.uflow.orderservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PurchaseOrderResponseDto {
    private Integer orderId;
    private String userId;
    private String productId;
    private Double amount;
    private OrderStatus status;
}
