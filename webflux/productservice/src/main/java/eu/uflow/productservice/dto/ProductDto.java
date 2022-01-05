package eu.uflow.productservice.dto;

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

    public ProductDto(final String description, final Double price) {
        this.description = description;
        this.price = price;
    }
}
