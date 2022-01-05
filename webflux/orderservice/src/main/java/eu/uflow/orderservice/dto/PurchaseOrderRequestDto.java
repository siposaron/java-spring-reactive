package eu.uflow.orderservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PurchaseOrderRequestDto {
    private String userId;
    private String productId;
}
