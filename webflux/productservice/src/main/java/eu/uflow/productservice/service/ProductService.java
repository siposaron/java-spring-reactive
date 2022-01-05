package eu.uflow.productservice.service;

import eu.uflow.productservice.dto.ProductDto;
import eu.uflow.productservice.entity.Product;
import eu.uflow.productservice.repository.ProductRepository;
import eu.uflow.productservice.util.EntityDtoUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final Sinks.Many<ProductDto> productStreamSink;

    public Flux<ProductDto> getAll() {
        return this.repository
                .findAll()
                .map(EntityDtoUtil::toDto);
    }

    public Flux<ProductDto> getAllByPrice(final Double minPrice, final Double maxPrice) {
        return this.repository
                .findByPriceBetween(minPrice, maxPrice)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> getById(final String id) {
        return this.repository
                .findById(id)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> createProduct(final Mono<ProductDto> productDto) {
        return productDto
                .map(EntityDtoUtil::toEntity)
                .flatMap(this.repository::insert)
                .map(EntityDtoUtil::toDto)
                .doOnSuccess(this.productStreamSink::tryEmitNext);
    }

    public Mono<ProductDto> updateProduct(final String id, final Mono<ProductDto> productDto) {
        return this.repository
                .findById(id)
                .flatMap(product -> productDto
                                .map(EntityDtoUtil::toEntity)
                                .doOnNext(prod -> prod.setId(id)))
                .flatMap(this.repository::save)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<Void> deleteProduct(final String id) {
        return this.repository.deleteById(id);
    }

}
