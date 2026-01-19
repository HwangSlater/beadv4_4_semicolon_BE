package dukku.semicolon.shared.payment.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * 환불 요청 DTO
 *
 * <p>
 * 결제 취소/환불 요청 시 사용
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRefundRequest {

    /**
     * 결제 UUID
     */
    @NotNull(message = "결제 UUID는 필수입니다.")
    private UUID paymentUuid;

    /**
     * 환불 금액
     */
    @Min(value = 1, message = "환불 금액은 1원 이상이어야 합니다.")
    private Integer cancelAmount;

    /**
     * 환불 사유
     */
    @NotBlank(message = "환불 사유는 필수입니다.")
    private String cancelReason;
}
