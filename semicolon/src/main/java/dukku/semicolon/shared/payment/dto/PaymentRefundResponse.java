package dukku.semicolon.shared.payment.dto;

import dukku.semicolon.boundedContext.payment.entity.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * 환불 응답 DTO
 *
 * <p>
 * 환불 처리 결과 반환
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRefundResponse {

    /**
     * 결제 UUID
     */
    private UUID paymentUuid;

    /**
     * 결제 상태
     */
    private PaymentStatus status;

    /**
     * 환불 금액
     */
    private Integer canceledAmount;

    /**
     * 환불 처리 일시
     */
    private OffsetDateTime canceledAt;

    /**
     * 결과 메시지
     */
    private String message;
}
