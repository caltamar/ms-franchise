package co.com.bancolombia.model.exception;

import co.com.bancolombia.model.exception.message.BusinessErrorMessage;

public class BusinessException extends RuntimeException {

    private final BusinessErrorMessage error;

    public BusinessException(BusinessErrorMessage error) {
        super(error.getMessage());
        this.error = error;
    }

    public BusinessErrorMessage getError() {
        return error;
    }
}
