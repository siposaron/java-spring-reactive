package eu.uflow.productservice.util;

import eu.uflow.productservice.dto.ProductDto;
import eu.uflow.productservice.entity.Product;

public class EntityDtoUtil {
    public static ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    public static Product toEntity(ProductDto productDto) {
        return Product.builder()
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .build();
    }
}
