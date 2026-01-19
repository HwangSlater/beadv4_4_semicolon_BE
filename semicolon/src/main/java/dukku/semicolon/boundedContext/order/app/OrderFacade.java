package dukku.semicolon.boundedContext.order.app;

import dukku.common.shared.order.type.OrderStatus;
import dukku.semicolon.boundedContext.order.entity.Order;
import dukku.semicolon.shared.order.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final UpdateOrderStatusUseCase updateOrderStatus;
    private final FindAdminOrderListUseCase findAdminOrderList;
    private final FindMyOrderListUseCase findMyOrderList;

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

    public void updateOrderStatusUseCase(UUID orderUuid, OrderStatus status) {
        updateOrderStatus.updateOrderStatus(orderUuid, status);
    }

    public Page<OrderListResponse> findAdminOrderList(AdminOrderSearchCondition condition, Pageable pageable) {
        return findAdminOrderList.execute(condition, pageable);
    }

    public Page<OrderListResponse> findMyOrderList(Pageable pageable) {
        return findMyOrderList.execute(pageable);
    }
}
