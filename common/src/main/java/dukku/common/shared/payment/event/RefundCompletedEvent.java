package dukku.common.shared.payment.event;

import dukku.common.shared.order.type.OrderStatus;

import java.util.UUID;

public record RefundCompletedEvent(UUID orderUuid, OrderStatus status, int refundAmount) {}
