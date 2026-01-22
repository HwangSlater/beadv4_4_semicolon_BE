package dukku.semicolon.shared.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;
import java.util.UUID;

public record ProductCreateRequest(
        @NotNull
        UUID sellerUuid,
        @NotNull
        @Min(1)
        Integer categoryId,
        @NotBlank
        String title,
        String description,
        @NotNull
        @PositiveOrZero
        Long price,
        @PositiveOrZero
        Long shippingFee,
        List<String> imageUrls
) {}
