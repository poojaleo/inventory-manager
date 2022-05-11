package com.shopify.inventoryservice.models.request;

public class GetShipmentRequest {
    private String companyName;
    private String shipmentId;

    public GetShipmentRequest() {
    }

    public GetShipmentRequest(Builder builder) {
        this.companyName = builder.companyName;
        this.shipmentId = builder.shipmentId;
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

    public static Builder builder() {return new Builder();}

    public static final class Builder {
        private String companyName;
        private String shipmentId;

        public Builder withCompanyName(String companyNameToUse) {
            this.companyName = companyNameToUse;
            return this;
        }

        public Builder withShipmentId(String shipmentIdToUse) {
            this.shipmentId = shipmentIdToUse;
            return this;
        }

        public GetShipmentRequest build() {
            return new GetShipmentRequest(this);
        }

    }
}
