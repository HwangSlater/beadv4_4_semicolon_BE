package dukku.semicolon.boundedContext.product.app;

import dukku.semicolon.boundedContext.product.entity.Product;
import dukku.semicolon.boundedContext.product.out.CategoryRepository;
import dukku.semicolon.boundedContext.product.out.ProductRepository;
import dukku.semicolon.shared.product.dto.ProductDetailResponse;
import dukku.semicolon.shared.product.dto.ProductUpdateRequest;
import dukku.semicolon.shared.product.exception.ProductCategoryNotFoundException;
import dukku.semicolon.shared.product.exception.ProductNotFoundException;
import dukku.semicolon.shared.product.exception.ProductUnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateProductUseCase {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductSupport productSupport;

    @Transactional
    public ProductDetailResponse execute(UUID userUuid, UUID productUuid, ProductUpdateRequest request) {

        Product product = productRepository.findByUuidAndDeletedAtIsNull(productUuid)
                .orElseThrow(ProductNotFoundException::new);

        if (!product.getSellerUuid().equals(userUuid)) {
            throw new ProductUnauthorizedException();
        }

        if (request.getCategoryId() != null) {
            Integer categoryId = request.getCategoryId();
            if (!categoryRepository.existsById(categoryId)) {
                throw new ProductCategoryNotFoundException();
            }
            product.changeCategory(categoryRepository.getReferenceById(categoryId));
        }

        if (request.getTitle() != null) product.changeTitle(request.getTitle());
        if (request.getDescription() != null) product.changeDescription(request.getDescription());
        if (request.getPrice() != null) product.changePrice(request.getPrice());
        if (request.getShippingFee() != null) product.changeShippingFee(request.getShippingFee());
        if (request.getConditionStatus() != null) product.changeConditionStatus(request.getConditionStatus());

        List<String> imageUrls = request.getImageUrls();

        // 이미지: 넘어오면 "전체 교체" (드래그 정렬 반영)
        if (imageUrls != null) {
            productSupport.validateMaxImageCount(imageUrls.size());
            product.replaceImages(imageUrls);
        }

        return ProductMapper.toDetail(product);
    }
}
