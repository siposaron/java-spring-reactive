package eu.uflow.orderservice.service;

import eu.uflow.orderservice.dto.PurchaseOrderResponseDto;
import eu.uflow.orderservice.repository.PurchaseOrderRepository;
import eu.uflow.orderservice.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final PurchaseOrderRepository orderRepository;

    public Flux<PurchaseOrderResponseDto> getOrdersByUserId(final String userId) {
        return Flux.fromStream(() -> this.orderRepository.findByUserId(userId).stream())
                .map(EntityDtoUtil::getPurchaseOrderDto)
                .subscribeOn(Schedulers.boundedElastic());
    }


}
