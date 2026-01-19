package dukku.semicolon.shared.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class OrderCreateRequest {
    @NotBlank
    private String address;
    @NotBlank
    private String recipient;
    @NotBlank
    private String contactNumber;
    @NotEmpty
    @Valid
    private List<OrderItemCreateRequest> items;

    @Getter
    @NoArgsConstructor
    public static class OrderItemCreateRequest {
        @NotNull
        private UUID productUuid;
        @NotNull
        private UUID sellerUuid;
        @NotBlank
        private String productName;
        @Positive
        private int productPrice;
        private String imageUrl;
    }
}

