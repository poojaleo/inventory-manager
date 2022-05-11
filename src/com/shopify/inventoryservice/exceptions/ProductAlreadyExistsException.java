package com.shopify.inventoryservice.exceptions;

public class ProductAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = -1875578230096520458L;

    public ProductAlreadyExistsException() {
    }

    public ProductAlreadyExistsException(String message) {
        super(message);
    }

    public ProductAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
