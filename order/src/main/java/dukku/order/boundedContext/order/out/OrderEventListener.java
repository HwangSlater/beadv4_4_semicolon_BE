package dukku.order.boundedContext.order.out;

import dukku.common.shared.payment.event.RefundCompletedEvent;
import dukku.order.boundedContext.order.app.UpdateOrderRefundStatusUseCase;
import dukku.order.boundedContext.order.app.UpdateOrderStatusUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class OrderEventListener {
    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;
    private final UpdateOrderRefundStatusUseCase updateOrderRefundStatusUseCase;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handle(RefundCompletedEvent event) {
        updateOrderRefundStatusUseCase.updateRefund(event.orderUuid(), event.status(), event.refundAmount());
    }
}
