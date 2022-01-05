package eu.uflow.userservice.service;

import eu.uflow.userservice.dto.TransactionRequestDto;
import eu.uflow.userservice.dto.TransactionResponseDto;
import eu.uflow.userservice.dto.TransactionStatus;
import eu.uflow.userservice.repository.UserRepository;
import eu.uflow.userservice.repository.UserTransactionRepository;
import eu.uflow.userservice.util.EntityDtoUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class TransactionService {
    private final UserTransactionRepository userTransactionRepository;
    private final UserRepository userRepository;
    private final TransactionalOperator op;

    public Mono<TransactionResponseDto> createTransaction(final String userId, final TransactionRequestDto dto) {
        return this.userRepository
                .updateUserBalance(userId, dto.getAmount())
                .filter(Boolean::booleanValue)
                .map(b -> EntityDtoUtil.toEntity(userId, dto))
                .flatMap(this.userTransactionRepository::save)
                .map(userTransaction -> EntityDtoUtil.toDto(userTransaction, TransactionStatus.APPROVED))
                .defaultIfEmpty(EntityDtoUtil.fromRequestToDto(userId, dto, TransactionStatus.DECLINED))
                .as(op::transactional); // transactional operation
    }

    public Flux<TransactionResponseDto> getTransactions(final String userId) {
        return this.userTransactionRepository
                .findByUserId(userId)
                .map(userTransaction -> EntityDtoUtil.toDto(userTransaction, TransactionStatus.APPROVED));
    }
}
