package com.shopify.inventoryservice.models.request;

import com.shopify.inventoryservice.dynamodb.table.ShipmentStatus;

import java.util.Objects;

public class UpdateShipmentRequest {
    private String companyName;
    private String shipmentId;
    private String shippingAddress;
    private ShipmentStatus status;
    private String trackingNumber;

    public UpdateShipmentRequest() {
    }

    public UpdateShipmentRequest(Builder builder) {
        this.companyName = builder.companyName;
        this.shipmentId = builder.shipmentId;
        this.shippingAddress = builder.shippingAddress;
        this.status = builder.status;
        this.trackingNumber = builder.trackingNumber;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateShipmentRequest that = (UpdateShipmentRequest) o;
        return companyName.equals(that.companyName) && shipmentId.equals(that.shipmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyName, shipmentId);
    }

    public static Builder builder() {return new Builder();}

    public static final class Builder {
        private String companyName;
        private String shipmentId;
        private String shippingAddress;
        private ShipmentStatus status;
        private String trackingNumber;

        public Builder withCompanyName(String companyNameToUse) {
            this.companyName = companyNameToUse;
            return this;
        }

        public Builder withShipmentId(String shipmentIdToUse) {
            this.shipmentId = shipmentIdToUse;
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

        public UpdateShipmentRequest build() {
            return new UpdateShipmentRequest(this);
        }


    }
}
