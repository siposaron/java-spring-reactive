package eu.uflow.orderservice.client.productservice;

import eu.uflow.orderservice.client.productservice.dto.ProductDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductClient {

    private final WebClient webClient;

    public ProductClient(
            @Value("${product.service.url}") String productServiceUrl) {
        this.webClient = WebClient
                .builder()
                .baseUrl(productServiceUrl)
                .build();
    }

    public Mono<ProductDto> getProductById(final String productId) {
        return this.webClient
                .get()
                .uri("{id}", productId)
                .retrieve()
                .bodyToMono(ProductDto.class);
    }

    public Flux<ProductDto> getProducts() {
        return this.webClient
                .get()
                .retrieve()
                .bodyToFlux(ProductDto.class);
    }
}
