package com.shopify.inventoryservice.exceptions;

public class InvalidPasswordException extends RuntimeException {

    private static final long serialVersionUID = 358888113542705041L;

    public InvalidPasswordException() {
    }

    public InvalidPasswordException(String message) {
        super(message);
    }

    public InvalidPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPasswordException(Throwable cause) {
        super(cause);
    }
}
