package co.com.bancolombia.api.dto;

import lombok.Builder;

@Builder
public record AddBranchRequest(
        String name
) {
}
