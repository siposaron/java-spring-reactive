package eu.uflow.orderservice.client.userservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TransactionRequestDto {
    private String userId;
    private Double amount;
}
