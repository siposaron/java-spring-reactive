package eu.uflow.userservice.repository;

import eu.uflow.userservice.entity.UserTransaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserTransactionRepository extends ReactiveCrudRepository<UserTransaction, String> {
    public Flux<UserTransaction> findByUserId(final String userId);
}
