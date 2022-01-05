package eu.uflow.orderservice.dto;

import eu.uflow.orderservice.client.productservice.dto.ProductDto;
import eu.uflow.orderservice.client.userservice.dto.TransactionRequestDto;
import eu.uflow.orderservice.client.userservice.dto.TransactionResponseDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@RequiredArgsConstructor
public class RequestContext {
    private final PurchaseOrderRequestDto purchaseOrderRequestDto;
    private ProductDto productDto;
    private TransactionRequestDto transactionRequestDto;
    private TransactionResponseDto transactionResponseDto;
}
