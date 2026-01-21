package dukku.semicolon.boundedContext.deposit.in;

import dukku.common.shared.payment.event.PaymentSuccessEvent;
import dukku.common.shared.payment.event.RefundCompletedEvent;
import dukku.semicolon.boundedContext.deposit.app.DepositFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class DepositEventListener {

    private final DepositFacade depositFacade;

    /**
     * 결제 성공 시 예치금 차감 처리
     *
     * <p>
     * PaymentSuccessEvent 수신 시 예치금을 차감한다.
     * 차감 성공 시 DepositUsedEvent 발행.
     * 차감 실패 시 DepositDeductionFailedEvent 발행 (보상 트랜잭션 유도).
     */
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(PaymentSuccessEvent event) {
        depositFacade.deductDepositForPayment(
                event.userUuid(),
                event.depositAmount(),
                event.orderUuid());
    }

    /**
     * 환불 완료 시 예치금 복구 처리
     *
     * <p>
     * RefundCompletedEvent 수신 시 예치금을 롤백(재적립)한다.
     * 복구 성공 시 DepositRefundedEvent 발행.
     */
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(RefundCompletedEvent event) {
        depositFacade.refundDeposit(
                event.userUuid(),
                event.depositRefundAmount(),
                event.orderUuid());
    }

    /*
     * TODO: 추후 정산(Settlement) 도메인 구현 및 이벤트(SettlementCompletedEvent) 정의 시 주석 해제 및
     * 구현
     *
     * 정산 완료 시 판매자 예치금을 입금(충전) 처리한다.
     *
     * @Async
     * 
     * @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
     * 
     * @Transactional(propagation = Propagation.REQUIRES_NEW)
     * public void handle(SettlementCompletedEvent event) {
     * if (event.amount() == null || event.amount() <= 0) {
     * return;
     * }
     * 
     * try {
     * increaseDepositUseCase.increase(
     * event.userUuid(), // 또는 event.depositUuid()로 조회
     * event.amount(),
     * DepositHistoryType.SETTLEMENT,
     * // event.orderItemUuid() // 정산 대상 아이템
     * );
     * 
     * // 성공 로그 or 이벤트
     * } catch (Exception e) {
     * log.error("[정산 예치금 충전 실패] ... ", e);
     * }
     * }
     */
}
