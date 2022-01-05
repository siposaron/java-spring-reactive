package eu.uflow.orderservice.client.productservice.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDto {
    private String id;
    private String description;
    private Double price;
}
