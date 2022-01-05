package eu.uflow.orderservice.service;

import eu.uflow.orderservice.client.productservice.ProductClient;
import eu.uflow.orderservice.client.userservice.UserClient;
import eu.uflow.orderservice.dto.PurchaseOrderRequestDto;
import eu.uflow.orderservice.dto.PurchaseOrderResponseDto;
import eu.uflow.orderservice.dto.RequestContext;
import eu.uflow.orderservice.repository.PurchaseOrderRepository;
import eu.uflow.orderservice.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class OrderFulfillmentService {
    private final ProductClient productClient;
    private final UserClient userClient;
    private final PurchaseOrderRepository orderRepository;

    public Mono<PurchaseOrderResponseDto> processOrder(final Mono<PurchaseOrderRequestDto> requestDto) {
        return requestDto
                .map(RequestContext::new)
                .flatMap(this::productRequestResponse)
                .doOnNext(EntityDtoUtil::setTransactionRequestDto)
                .flatMap(this::userRequestResponse)
                .map(EntityDtoUtil::getPurchaseOrder)
                .map(this.orderRepository::save) // blocking!
                .map(EntityDtoUtil::getPurchaseOrderDto)
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<RequestContext> productRequestResponse(final RequestContext context) {
        return this.productClient
                .getProductById(context.getPurchaseOrderRequestDto().getProductId())
                .doOnNext(context::setProductDto)
                .retryWhen(Retry.backoff(3, Duration.ofMillis(300)))
                .thenReturn(context);
    }

    private Mono<RequestContext> userRequestResponse(final RequestContext context) {
        return this.userClient
                .authorizeTransaction(context.getTransactionRequestDto())
                .doOnNext(context::setTransactionResponseDto)
                .retryWhen(Retry.backoff(3, Duration.ofMillis(300)))
                .thenReturn(context);
    }

}
