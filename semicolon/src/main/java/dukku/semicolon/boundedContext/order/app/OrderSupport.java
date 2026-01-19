package dukku.semicolon.boundedContext.order.app;

import dukku.semicolon.boundedContext.order.entity.Order;
import dukku.semicolon.boundedContext.order.out.OrderRepository;
import dukku.order.shared.order.exception.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderSupport {
    private OrderRepository orderRepository;

    public Order findByUuid(UUID orderUuid) {
        return orderRepository.findByUuid(orderUuid)
                .orElseThrow(OrderNotFoundException::new);
    }

    public Order findByUuidWithItems(UUID orderUuid) {
        return orderRepository.findByUuidWithItems(orderUuid)
                .orElseThrow(OrderNotFoundException::new);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }
}
