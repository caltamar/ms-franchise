package co.com.bancolombia.model.exception.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessErrorMessage {

    FRANCHISE_NOT_FOUND(
            "F001",
                    "Franchise not found"
    ),

    BRANCH_NOT_FOUND(
            "F002",
                    "Branch not found"
    ),

    PRODUCT_NOT_FOUND(
            "F003",
                    "Product not found"
    );

    private final String code;
    private final String message;
}
