package eu.uflow.productservice.config;

import eu.uflow.productservice.dto.ProductDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class ProductStreamConfig {

    @Bean
    public Sinks.Many<ProductDto> productStreamSink() {
        return Sinks.many().replay().limit(1);
    }

    @Bean
    public Flux<ProductDto> productStream(Sinks.Many<ProductDto> productStreamSink) {
        return productStreamSink.asFlux();
    }

}
