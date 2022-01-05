package eu.uflow.orderservice;

import eu.uflow.orderservice.client.productservice.ProductClient;
import eu.uflow.orderservice.client.productservice.dto.ProductDto;
import eu.uflow.orderservice.client.userservice.UserClient;
import eu.uflow.orderservice.client.userservice.dto.UserDto;
import eu.uflow.orderservice.dto.PurchaseOrderRequestDto;
import eu.uflow.orderservice.service.OrderFulfillmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class OrderserviceApplicationTests {

	@Autowired
	private UserClient userClient;
	@Autowired
	private ProductClient productClient;
	@Autowired
	private OrderFulfillmentService orderFulfillmentService;


//	@Test
	void contextLoads() {
		final var dtoFlux = Flux
				.zip(
					userClient.getUsers(),
					productClient.getProducts())
				.map(t -> this.buildDto(t.getT1(), t.getT2()))
				.flatMap(dto -> this.orderFulfillmentService.processOrder(Mono.just(dto)))
				.doOnNext(System.out::println);

		StepVerifier.create(dtoFlux)
				.expectNextCount(3)
				.verifyComplete();
	}

	private PurchaseOrderRequestDto buildDto(UserDto userDto, ProductDto productDto) {
		final var dto = new PurchaseOrderRequestDto();
		dto.setUserId(userDto.getId());
		dto.setProductId(productDto.getId());
		return dto;
	}

}
