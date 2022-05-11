package com.shopify.inventoryservice.models;

import com.shopify.inventoryservice.dynamodb.table.ShipmentStatus;

import java.util.Map;

public class ShipmentModel {
    private String companyName;
    private String shipmentId;
    private String createdAt;
    private String shippingAddress;
    private ShipmentStatus status;
    private String trackingNumber;
    private Map<String, Integer> productsShipped;

    public ShipmentModel() {
    }

    public ShipmentModel(Builder builder) {
        this.companyName = builder.companyName;
        this.shipmentId = builder.shipmentId;
        this.createdAt = builder.createdAt;
        this.shippingAddress = builder.shippingAddress;
        this.status = builder.status;
        this.trackingNumber = builder.trackingNumber;
        this.productsShipped = builder.productsShipped;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public Map<String, Integer> getProductsShipped() {
        return productsShipped;
    }

    public void setProductsShipped(Map<String, Integer> productsShipped) {
        this.productsShipped = productsShipped;
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

        public ShipmentModel build() {
            return new ShipmentModel(this);
        }

    }

}
