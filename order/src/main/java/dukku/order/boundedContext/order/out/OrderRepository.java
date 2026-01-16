package dukku.order.boundedContext.order.out;

import dukku.order.boundedContext.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findByUuid(UUID orderUuid);
}
