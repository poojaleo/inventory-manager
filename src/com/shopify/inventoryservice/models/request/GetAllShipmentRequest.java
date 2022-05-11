package com.shopify.inventoryservice.models.request;

public class GetAllShipmentRequest {
    private String companyName;

    public GetAllShipmentRequest() {
    }

    public GetAllShipmentRequest(Builder builder) {
        this.companyName = builder.companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public static Builder builder() {return new Builder();}

    public static final class Builder {
        private String companyName;


        public Builder withCompanyName(String companyNameToUse) {
            this.companyName = companyNameToUse;
            return this;
        }

        public GetAllShipmentRequest build() {
            return new GetAllShipmentRequest(this);
        }

    }
}
