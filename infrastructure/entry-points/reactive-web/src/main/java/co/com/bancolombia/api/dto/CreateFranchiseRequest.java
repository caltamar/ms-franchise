package co.com.bancolombia.api.dto;

import lombok.Builder;

@Builder
public record CreateFranchiseRequest(
        String name
) {
}
