package com.shopify.inventoryservice.exceptions;

public class CompanyNameAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 5180375468873157768L;

    public CompanyNameAlreadyExistsException() {
    }

    public CompanyNameAlreadyExistsException(String message) {
        super(message);
    }

    public CompanyNameAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompanyNameAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
