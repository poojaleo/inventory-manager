package com.shopify.inventoryservice.exceptions;

public class CompanyNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1776574263255317508L;

    public CompanyNotFoundException() {
        super();
    }

    public CompanyNotFoundException(String message) {
        super(message);
    }

    public CompanyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompanyNotFoundException(Throwable cause) {
        super(cause);
    }
}
