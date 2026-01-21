package dukku.semicolon.boundedContext.deposit.app;

import dukku.semicolon.boundedContext.deposit.entity.DepositHistory;
import dukku.semicolon.boundedContext.deposit.entity.enums.DepositHistoryType;
import dukku.semicolon.shared.deposit.dto.DepositDto;
import dukku.semicolon.shared.deposit.dto.DepositHistoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DepositFacade {

    private final FindDepositUseCase findDepositUseCase;
    private final IncreaseDepositUseCase increaseDepositUseCase;
    private final DecreaseDepositUseCase decreaseDepositUseCase;
    private final FindDepositHistoriesUseCase findDepositHistoriesUseCase;

    /**
     * 사용자 예치금 조회
     *
     * <p>
     * 사용자의 예치금 정보를 조회하고, 존재하지 않을 경우 0원의 예치금 계좌를 생성하여 반환한다.
     */
    public DepositDto findDeposit(UUID userUuid) {
        return findDepositUseCase.findOrCreate(userUuid).toDto();
    }

    /**
     * 예치금 잔액 증가 (충전/정산/환불)
     *
     * <p>
     * 예치금 잔액을 증가시키고 해당 내역을 저장한다.
     */
    public void increaseDeposit(UUID userUuid, Long amount, DepositHistoryType type, UUID orderItemUuid) {
        increaseDepositUseCase.increase(userUuid, amount, type, orderItemUuid);
    }

    /**
     * 예치금 잔액 차감 (사용/롤백)
     *
     * <p>
     * 예치금 잔액을 차감하고 해당 내역을 저장한다. 잔액 부족 시 예외 발생.
     */
    public void decreaseDeposit(UUID userUuid, Long amount, DepositHistoryType type, UUID orderItemUuid) {
        decreaseDepositUseCase.decrease(userUuid, amount, type, orderItemUuid);
    }

    /**
     * 예치금 변동 내역 조회
     *
     * <p>
     * 사용자의 모든 예치금 변동 내역을 최신순으로 조회한다.
     */
    public List<DepositHistoryDto> findHistories(UUID userUuid) {
        return findDepositHistoriesUseCase.findHistories(userUuid).stream()
                .map(DepositHistory::toDto)
                .toList();
    }

}
