package eu.uflow.userservice.controller;

import eu.uflow.userservice.dto.TransactionRequestDto;
import eu.uflow.userservice.dto.TransactionResponseDto;
import eu.uflow.userservice.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("users/{userId}/transactions")
@AllArgsConstructor
public class UserTransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public Mono<TransactionResponseDto> createTransaction(
            @PathVariable final String userId,
            @RequestBody final Mono<TransactionRequestDto> dto) {
        return dto.flatMap(d -> this.transactionService.createTransaction(userId, d));
    }

    @GetMapping
    public Flux<TransactionResponseDto> getTransactions(
            @PathVariable final String userId) {
        return this.transactionService.getTransactions(userId);
    }

}
