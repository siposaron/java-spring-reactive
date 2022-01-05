package eu.uflow.orderservice.util;

import eu.uflow.orderservice.client.userservice.dto.TransactionRequestDto;
import eu.uflow.orderservice.client.userservice.dto.TransactionStatus;
import eu.uflow.orderservice.dto.OrderStatus;
import eu.uflow.orderservice.dto.PurchaseOrderResponseDto;
import eu.uflow.orderservice.dto.RequestContext;
import eu.uflow.orderservice.entity.PurchaseOrder;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

    public static PurchaseOrderResponseDto getPurchaseOrderDto(final PurchaseOrder order) {
        final PurchaseOrderResponseDto dto = new PurchaseOrderResponseDto();
        BeanUtils.copyProperties(order, dto);
        dto.setOrderId(order.getId());
        return dto;
    }

    public static void setTransactionRequestDto(final RequestContext context) {
        final TransactionRequestDto dto = new TransactionRequestDto();
        dto.setUserId(context.getPurchaseOrderRequestDto().getUserId());
        dto.setAmount(context.getProductDto().getPrice());
        context.setTransactionRequestDto(dto);
    }

    public static PurchaseOrder getPurchaseOrder(final RequestContext context) {
        final PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setUserId(context.getPurchaseOrderRequestDto().getUserId());
        purchaseOrder.setProductId(context.getPurchaseOrderRequestDto().getProductId());
        purchaseOrder.setAmount(context.getProductDto().getPrice());
        final TransactionStatus transactionStatus = context.getTransactionResponseDto().getStatus();
        final OrderStatus status = TransactionStatus.APPROVED.equals(transactionStatus) ? OrderStatus.COMPLETED : OrderStatus.FAILED;
        purchaseOrder.setStatus(status);
        return purchaseOrder;
    }
}
