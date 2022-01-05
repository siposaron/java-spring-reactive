package eu.uflow.userservice.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@ToString
@Table("user_transactions")
public class UserTransaction {
    @Id
    private String id;
    private String userId;
    private Double amount;
    private LocalDateTime transactionDate;
}
