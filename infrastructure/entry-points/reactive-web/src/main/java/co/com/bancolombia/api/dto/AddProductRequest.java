package co.com.bancolombia.api.dto;

import lombok.Builder;

@Builder
public record AddProductRequest(
        String name,
        Integer stock
) {
}
