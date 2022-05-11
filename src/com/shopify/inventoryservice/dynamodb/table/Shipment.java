package com.shopify.inventoryservice.dynamodb.table;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.shopify.inventoryservice.dynamodb.tableconverters.ShipmentMapTypeConverter;

import java.util.Map;
import java.util.Objects;

@DynamoDBTable(tableName = "Shipments")
public class Shipment {
    private String companyName;
    private String shipmentId;
    private String createdAt;
    private String shippingAddress;
    private ShipmentStatus status;
    private String trackingNumber;
    private Map<String, Integer> productsShipped; //sku num, quantity

    public Shipment() {
    }

    public Shipment(Builder builder) {
        this.companyName = builder.companyName;
        this.shipmentId = builder.shipmentId;
        this.createdAt = builder.createdAt;
        this.shippingAddress = builder.shippingAddress;
        this.status = builder.status;
        this.trackingNumber = builder.trackingNumber;
        this.productsShipped = builder.productsShipped;
    }

    @DynamoDBHashKey(attributeName = "companyName")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @DynamoDBRangeKey(attributeName = "shipmentId")
    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    @DynamoDBAttribute(attributeName = "createdAt")
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @DynamoDBAttribute(attributeName = "shippingAddress")
    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "status")
    public ShipmentStatus getStatus() {
        return status;
    }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }

    @DynamoDBAttribute(attributeName = "trackingNumber")
    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    @DynamoDBTypeConverted(converter = ShipmentMapTypeConverter.class)
    @DynamoDBAttribute(attributeName = "productsShipped")
    public Map<String, Integer> getProductsShipped() {
        return productsShipped;
    }

    public void setProductsShipped(Map<String, Integer> productsShipped) {
        this.productsShipped = productsShipped;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shipment shipment = (Shipment) o;
        return shipmentId == shipment.shipmentId && createdAt.equals(shipment.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shipmentId, createdAt);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String companyName;
        private String shipmentId;
        private String createdAt;
        private String shippingAddress;
        private ShipmentStatus status;
        private String trackingNumber;
        private Map<String, Integer> productsShipped;

        public Builder withCompanyName(String companyNameToUse) {
            this.companyName = companyNameToUse;
            return this;
        }

        public Builder withShipmentId(String shipmentIdToUse) {
            this.shipmentId = shipmentIdToUse;
            return this;
        }

        public Builder withCreatedAt(String createdAtToUse) {
            this.createdAt = createdAtToUse;
            return this;
        }

        public Builder withShippingAddress(String shippingAddressToUse) {
            this.shippingAddress = shippingAddressToUse;
            return this;
        }

        public Builder withStatus(ShipmentStatus statusToUse) {
            this.status = statusToUse;
            return this;
        }

        public Builder withTrackingNumber(String trackingNumberToUse) {
            this.trackingNumber = trackingNumberToUse;
            return this;
        }

        public Builder withProductsShipped(Map<String,Integer> productsShippedToUse) {
            this.productsShipped = productsShippedToUse;
            return this;
        }

        public Shipment build() {
            return new Shipment(this);
        }

    }

}
