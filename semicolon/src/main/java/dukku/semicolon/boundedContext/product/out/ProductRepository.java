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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, Integer>, CustomProductRepository {
    Optional<Integer> findIdByUuidAndDeletedAtIsNull(UUID productUuid);

    Optional<Product> findByUuid(UUID productUuid);

    Optional<Product> findByUuidAndDeletedAtIsNull(UUID productUuid);

    List<Product> findAllByUuidIn(List<UUID> uuids);

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
}
