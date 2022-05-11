package com.shopify.inventoryservice.exceptions;

public class ProductNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 7034443743135231630L;

    public ProductNotFoundException() {
        super();
    }

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductNotFoundException(Throwable cause) {
        super(cause);
    }
}
