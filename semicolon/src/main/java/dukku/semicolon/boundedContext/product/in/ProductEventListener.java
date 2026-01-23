package dukku.semicolon.boundedContext.product.in;

import dukku.semicolon.boundedContext.product.app.cqrs.SaveToElasticSearchUseCase;
import dukku.semicolon.boundedContext.product.app.cqrs.SyncProductSearchStatsUseCase;
import dukku.semicolon.boundedContext.product.out.ProductSearchRepository;
import dukku.semicolon.shared.product.event.ProductCreatedEvent;
import dukku.semicolon.shared.product.event.ProductDeletedEvent;
import dukku.semicolon.shared.product.event.ProductStatsBulkUpdatedEvent;
import dukku.semicolon.shared.product.event.ProductUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductEventListener {
    private final ProductSearchRepository productSearchRepository;
    private final SaveToElasticSearchUseCase saveToElasticSearchUseCase;
    private final SyncProductSearchStatsUseCase  syncProductSearchStatsUseCase;

    // 1. 생성 동기화
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void syncCreate(ProductCreatedEvent event) {
        saveToElasticSearchUseCase.execute(event.product());
    }

    // 2. 수정 동기화
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void syncUpdate(ProductUpdatedEvent event) {
        log.info("Sync Update Product: {}", event.product().getId());

        saveToElasticSearchUseCase.execute(event.product());
    }

    // 3. 삭제 동기화
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void syncDelete(ProductDeletedEvent event) {
        String docId = String.valueOf(event.product().getId());
        log.info("Sync Delete Product: {}", docId);

        productSearchRepository.deleteById(docId);
    }

    // 4. 통계 동기화 (배치 작업 후 실행)
    @Async
    @EventListener
    public void syncStats(ProductStatsBulkUpdatedEvent event) {
        syncProductSearchStatsUseCase.execute(event.getStats());
    }
}