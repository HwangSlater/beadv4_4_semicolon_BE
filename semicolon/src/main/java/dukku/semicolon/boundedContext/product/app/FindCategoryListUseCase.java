package dukku.semicolon.boundedContext.product.app;

import dukku.semicolon.boundedContext.product.out.CategoryRepository;
import dukku.semicolon.shared.product.dto.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindCategoryListUseCase {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> execute() {
        return categoryRepository.findAll().stream()
                .map(CategoryResponse::from)
                .toList();
    }
}
