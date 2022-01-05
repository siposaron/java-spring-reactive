package eu.uflow.productservice.repository;

import eu.uflow.productservice.entity.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

    public Flux<Product> findByPriceBetween(final Double minPrice, final Double maxPrice);

}
