package dukku.common.shared.deposit.event;

import java.util.UUID;

/**
 * 예치금 충전 실패 이벤트
 *
 * <p>
 * 정산 등을 통한 예치금 충전 시도가 실패했을 때 내부적으로 발행.
 * 실패 원인(Reason) 포함.
 */
public record DepositChargeFailedEvent(
        UUID depositUuid,
        Long amount,
        UUID orderItemUuid,
        String reason) {
}
