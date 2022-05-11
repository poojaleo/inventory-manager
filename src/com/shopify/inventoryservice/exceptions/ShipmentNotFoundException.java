package com.shopify.inventoryservice.exceptions;

public class ShipmentNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 3537796377262204286L;

    public ShipmentNotFoundException() {
    }

    public ShipmentNotFoundException(String message) {
        super(message);
    }

    public ShipmentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShipmentNotFoundException(Throwable cause) {
        super(cause);
    }
}
