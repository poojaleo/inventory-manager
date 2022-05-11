package com.shopify.inventoryservice.exceptions;

public class InvalidAttributeValueException extends RuntimeException {
    private static final long serialVersionUID = -2640915272958341413L;

    public InvalidAttributeValueException() {
    }

    public InvalidAttributeValueException(String message) {
        super(message);
    }

    public InvalidAttributeValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAttributeValueException(Throwable cause) {
        super(cause);
    }
}
