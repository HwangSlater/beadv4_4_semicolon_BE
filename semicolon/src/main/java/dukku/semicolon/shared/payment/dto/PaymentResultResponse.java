package dukku.semicolon.shared.payment.dto;

import dukku.semicolon.boundedContext.payment.entity.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * 결제 내역 상세 조회 응답 DTO
 *
 * <p>
 * 결제 상세 정보 조회 시 반환
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResultResponse {

    /**
     * 결제 UUID
     */
    private UUID paymentUuid;

    /**
     * 토스 주문 ID
     */
    private String orderId;

    /**
     * 주문명
     */
    private String orderName;

    /**
     * 결제 상태
     */
    private PaymentStatus status;

    /**
     * 총 결제 금액
     */
    private Integer totalAmount;

    /**
     * PG 결제 금액
     */
    private Integer pgAmount;

    /**
     * 예치금 사용 금액
     */
    private Integer depositAmount;

    /**
     * 쿠폰 할인 금액
     */
    private Integer couponAmount;

    /**
     * 승인 일시
     */
    private OffsetDateTime approvedAt;

    /**
     * 결제 주문 상품 목록
     */
    private List<PaymentOrderItemDto> items;
}
