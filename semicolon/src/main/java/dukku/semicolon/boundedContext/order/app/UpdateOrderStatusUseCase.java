package dukku.semicolon.boundedContext.order.app;

import dukku.semicolon.boundedContext.order.entity.Order;
import dukku.common.shared.order.type.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateOrderStatusUseCase {
    private final OrderSupport orderSupport;

    @Transactional
    public void updateOrderStatus(UUID orderUuid, OrderStatus newStatus) {
        Order order = orderSupport.findByUuid(orderUuid);

        if (Objects.nonNull(newStatus)) {
            order.updateOrderStatus(newStatus);
        }
    }
}
