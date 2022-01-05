package eu.uflow.productservice.controller;

import eu.uflow.productservice.dto.ProductDto;
import eu.uflow.productservice.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final Flux<ProductDto> productStream;

    @GetMapping(value = "stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDto> getStream(
            @RequestParam(required = true) final Double maxPrice
    ) {
        System.out.println(maxPrice);
        return this.productStream
                .filter(dto -> dto.getPrice() <= maxPrice);
    }

    @GetMapping
    public Flux<ProductDto> getAll(
            @RequestParam(required = false) final Double minPrice,
            @RequestParam(required = false) final Double maxPrice
    ) {
        System.out.println(minPrice + " / " + maxPrice);
        if (Optional.ofNullable(minPrice).isEmpty() || Optional.ofNullable(maxPrice).isEmpty()) {
            return this.productService
                    .getAll();
        } else {
            return this.productService.getAllByPrice(minPrice, maxPrice);
        }
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductDto>> getById(
            @PathVariable final String id) {
        return this.productService
                .getById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ProductDto> createProduct(
            @RequestBody final Mono<ProductDto> productDto) {
        return this.productService
                .createProduct(productDto);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ProductDto>> updateProduct(
            @PathVariable final String id,
            @RequestBody final Mono<ProductDto> productDto) {
        return this.productService
                .updateProduct(id, productDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProduct(
            @PathVariable final String id) {
        return this.productService
                .deleteProduct(id);
    }
}
