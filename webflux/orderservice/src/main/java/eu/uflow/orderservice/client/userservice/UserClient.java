package eu.uflow.orderservice.client.userservice;

import eu.uflow.orderservice.client.productservice.dto.ProductDto;
import eu.uflow.orderservice.client.userservice.dto.TransactionRequestDto;
import eu.uflow.orderservice.client.userservice.dto.TransactionResponseDto;
import eu.uflow.orderservice.client.userservice.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserClient {

    private final WebClient webClient;

    public UserClient(
            @Value("${user.service.url}") String userServiceUrl) {
        this.webClient = WebClient
                .builder()
                .baseUrl(userServiceUrl)
                .build();
    }

    public Mono<TransactionResponseDto> authorizeTransaction(final TransactionRequestDto dto) {
        return this.webClient
                .post()
                .uri("{userId}/transactions", dto.getUserId())
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(TransactionResponseDto.class);
    }

    public Flux<UserDto> getUsers() {
        return this.webClient
                .get()
                .retrieve()
                .bodyToFlux(UserDto.class);
    }
}
