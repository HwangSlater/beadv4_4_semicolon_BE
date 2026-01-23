package dukku.semicolon.boundedContext.product.out;

import dukku.common.shared.product.type.VisibilityStatus;
import dukku.semicolon.boundedContext.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, Integer>, CustomProductRepository {
    Optional<Integer> findIdByUuidAndDeletedAtIsNull(UUID productUuid);

    Optional<Product> findByUuid(UUID productUuid);

    Optional<Product> findByUuidAndDeletedAtIsNull(UUID productUuid);

    boolean existsByUuidAndDeletedAtIsNull(UUID productUuid);

    // 목록: visibility=VISIBLE, deletedAt=null 기본
    Page<Product> findByVisibilityStatusAndDeletedAtIsNull(
            VisibilityStatus visibilityStatus,
            Pageable pageable
    );

    // 카테고리 필터 목록
    Page<Product> findByCategory_IdAndVisibilityStatusAndDeletedAtIsNull(
            Integer categoryId,
            VisibilityStatus visibilityStatus,
            Pageable pageable
    );

    // 스케줄러용: Bulk Update (영속성 컨텍스트 무시하고 DB 바로 쏘기)
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Product p SET p.viewCount = :viewCount, p.likeCount = :likeCount, p.commentCount = :commentCount WHERE p.id = :id")
    void updateProductStats(@Param("id") Long id, @Param("viewCount") long viewCount, @Param("likeCount") long likeCount, @Param("commentCount") long commentCount);
}
