package dukku.order.boundedContext.order.app;

import dukku.order.boundedContext.order.entity.Order;
import dukku.order.shared.order.dto.OrderCreateRequest;
import dukku.order.shared.order.dto.OrderResponse;
import dukku.order.shared.order.dto.OrderUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderFacade {
    private final CreateOrderUseCase createOrder;
    private final FindOrderUseCase findOrder;
    private final UpdateShippingInfoUseCase updateShippingInfo;

    public OrderResponse createOrder(OrderCreateRequest req) {
        return Order.toOrderResponse(createOrder.execute(req));
    }

    @Transactional(readOnly = true)
    public OrderResponse findOrderByUuid(UUID orderUuid) {
        return Order.toOrderResponse(findOrder.execute(orderUuid));
    }

    public void updateShippingInfo(UUID orderUuid, OrderUpdateRequest.ShippingInfo req) {
        updateShippingInfo.execute(orderUuid, req);
    }
}
