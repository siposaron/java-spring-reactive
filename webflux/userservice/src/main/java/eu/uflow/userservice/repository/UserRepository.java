package eu.uflow.userservice.repository;

import eu.uflow.userservice.entity.User;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, String> {

    @Modifying
    @Query("update users set balance = balance - :amount " +
                "where id = :userId and balance >= :amount")
    public Mono<Boolean> updateUserBalance(final String userId, final Double amount);
}
