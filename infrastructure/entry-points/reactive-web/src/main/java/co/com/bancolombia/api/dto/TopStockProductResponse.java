package co.com.bancolombia.api.dto;

import lombok.Builder;

@Builder
public record TopStockProductResponse(
        String branchId,
        String branchName,
        String productId,
        String productName,
        Integer stock
) {
}
