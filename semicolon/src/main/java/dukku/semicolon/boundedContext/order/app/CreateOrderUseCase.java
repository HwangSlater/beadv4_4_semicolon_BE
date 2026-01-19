package dukku.semicolon.boundedContext.order.app;

import dukku.common.global.UserUtil;
import dukku.semicolon.boundedContext.order.entity.Order;
import dukku.semicolon.boundedContext.order.entity.OrderItem;
import dukku.semicolon.shared.order.dto.OrderCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreateOrderUseCase {
    private final OrderSupport orderSupport;

    @Transactional
    public Order execute(OrderCreateRequest req) {
        Order order = Order.createOrder(req, UserUtil.getUserId());

        req.getItems().stream()
                .map(OrderItem::createOrderItem)
                .forEach(order::addOrderItem);

        return orderSupport.save(order);
    }
}