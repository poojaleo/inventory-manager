package com.shopify.inventoryservice.dynamodb.table;

public enum ShipmentStatus {
    LABELCREATED,
    INTRANSIT,
    COMPLETED,
    DELAYED,
    FAILED,
    UNKNOWN
}
