package dukku.order.boundedContext.order.app;

import dukku.order.boundedContext.order.entity.Order;
import dukku.common.shared.order.type.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateOrderRefundStatusUseCase {
    private final OrderSupport orderSupport;

    @Transactional
    public void updateRefund(UUID orderUuid, OrderStatus newStatus, int refundAmount) {
        Order order = orderSupport.findByUuid(orderUuid);

        if (newStatus != null) {
            order.updateOrderStatus(newStatus);
        }

        if (refundAmount > 0) {
            order.updateRefundedAmount(refundAmount);
        }
    }
}
