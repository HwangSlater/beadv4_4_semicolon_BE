package dukku.order.boundedContext.order.app;

import dukku.common.global.UserUtil;
import dukku.common.global.exception.ForbiddenException;
import dukku.order.boundedContext.order.entity.Order;
import dukku.order.shared.order.dto.OrderUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateShippingInfoUseCase {
    private final OrderSupport orderSupport;

    @Transactional
    public void execute(UUID orderUuid, OrderUpdateRequest.ShippingInfo req) {
        Order order = orderSupport.findByUuid(orderUuid);

        if (!order.getUserUuid().equals(UserUtil.getUserId())) {
            throw new ForbiddenException("주문 수정 권한이 없습니다.");
        }

        order.updateOrderForUser(req.getAddress(), req.getRecipient(), req.getContactNumber());
    }
}