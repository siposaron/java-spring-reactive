package eu.uflow.productservice.service;

import eu.uflow.productservice.dto.ProductDto;
import io.netty.util.internal.ThreadLocalRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class InitDBService implements CommandLineRunner {

    @Autowired
    private ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        final ProductDto p1 = new ProductDto();
        p1.setDescription("iPhone");
        p1.setPrice(459.21);

        final ProductDto p2 = new ProductDto();
        p2.setDescription("Moto 60");
        p2.setPrice(259.10);

        final ProductDto p3 = new ProductDto();
        p3.setDescription("iPad");
        p3.setPrice(599.00);

        Flux.just(p1, p2, p3)
                .concatWith(fluxOfProducts())
                .flatMap(p -> this.productService.createProduct(Mono.just(p)))
                .subscribe(System.out::println);
    }

    private Flux<ProductDto> fluxOfProducts() {
        return Flux
                .range(1, 100)
                .delayElements(Duration.ofSeconds(1))
                .map(i -> new ProductDto("Product " + i, ThreadLocalRandom.current().nextDouble(10.0, 200.0)));
    }
}
