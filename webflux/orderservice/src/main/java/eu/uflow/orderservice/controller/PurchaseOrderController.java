package eu.uflow.orderservice.controller;

import eu.uflow.orderservice.dto.PurchaseOrderRequestDto;
import eu.uflow.orderservice.dto.PurchaseOrderResponseDto;
import eu.uflow.orderservice.service.OrderFulfillmentService;
import eu.uflow.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final OrderFulfillmentService orderFulfillmentService;
    private final OrderService orderService;

    @PostMapping
    public Mono<ResponseEntity<PurchaseOrderResponseDto>> createOrder(
            @RequestBody
            final Mono<PurchaseOrderRequestDto> orderRequestDto) {
        return this.orderFulfillmentService
                .processOrder(orderRequestDto)
                .map(ResponseEntity::ok)
                .onErrorReturn(WebClientResponseException.class, ResponseEntity.badRequest().build())
                .onErrorReturn(WebClientRequestException.class, ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
    }

    @GetMapping("users/{userId}")
    public Flux<PurchaseOrderResponseDto> getOrdersOfUser(
            @PathVariable final String userId) {
        return this.orderService.getOrdersByUserId(userId);
    }
}
