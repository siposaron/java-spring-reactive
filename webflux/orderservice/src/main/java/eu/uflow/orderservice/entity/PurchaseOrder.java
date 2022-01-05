package eu.uflow.orderservice.entity;

import eu.uflow.orderservice.dto.OrderStatus;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@ToString
@Entity
public class PurchaseOrder {

    @Id
    @GeneratedValue
    private Integer id;

    private String productId;
    private String userId;
    private Double amount;
    private OrderStatus status;

}
