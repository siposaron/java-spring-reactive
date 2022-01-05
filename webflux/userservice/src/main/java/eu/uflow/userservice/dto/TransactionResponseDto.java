package eu.uflow.userservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TransactionResponseDto {
    private String id;
    private String userId;
    private Double amount;
    private TransactionStatus status;
}
