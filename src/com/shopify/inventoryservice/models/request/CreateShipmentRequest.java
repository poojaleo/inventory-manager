package com.shopify.inventoryservice.models.request;

import java.util.Map;

public class CreateShipmentRequest {
    private String companyName;
    private String shippingAddress;
    private Map<String, Integer> productsShipped;

    public CreateShipmentRequest() {
    }

    public CreateShipmentRequest(Builder builder) {
        this.companyName = builder.companyName;
        this.shippingAddress = builder.shippingAddress;
        this.productsShipped = builder.productsShipped;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Map<String, Integer> getProductsShipped() {
        return productsShipped;
    }

    public void setProductsShipped(Map<String, Integer> productsShipped) {
        this.productsShipped = productsShipped;
    }


    public static Builder builder() {return new Builder();}

    public static final class Builder {
        private String companyName;
        private String shippingAddress;
        private Map<String, Integer> productsShipped;

        public Builder withCompanyName(String companyNameToUse) {
            this.companyName = companyNameToUse;
            return this;
        }

        public Builder withShippingAddress(String shippingAddressToUse) {
            this.shippingAddress = shippingAddressToUse;
            return this;
        }

        public Builder withProductsShipped(Map<String,Integer> productsShippedToUse) {
            this.productsShipped = productsShippedToUse;
            return this;
        }

        public CreateShipmentRequest build() {
            return new CreateShipmentRequest(this);
        }


    }
}
