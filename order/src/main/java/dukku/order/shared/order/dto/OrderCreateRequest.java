package dukku.order.shared.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class OrderCreateRequest {
    private String address;
    private String recipient;
    private String contactNumber;
    private List<OrderItemCreateRequest> items; // 주문 상품 리스트

    @Getter
    @NoArgsConstructor
    public static class OrderItemCreateRequest {
        private UUID productUuid;
        private UUID sellerUuid;
        private String productName;
        private int productPrice;
        private String imageUrl;
    }
}
