package dukku.common.shared.deposit.event;

import java.util.UUID;

/**
 * 예치금 충전 성공 이벤트
 *
 * <p>
 * 정산 등을 통해 예치금 충전이 성공했을 때 내부적으로 발행.
 */
public record DepositChargeSucceededEvent(
        UUID depositUuid,
        Long amount,
        UUID orderItemUuid) {
}
