package com.shopify.inventoryservice.models.request;


import java.util.Objects;

public class GetProductRequest {
    private String companyName;
    private String sku;

    public GetProductRequest() {
    }

    public GetProductRequest(Builder builder) {
        this.companyName = builder.companyName;
        this.sku = builder.sku;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetProductRequest that = (GetProductRequest) o;
        return Objects.equals(companyName, that.companyName) && Objects.equals(sku, that.sku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyName, sku);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String companyName;
        private String sku;

        public Builder() {
        }

        public Builder withCompanyName(String companyNameToUse) {
            this.companyName = companyNameToUse;
            return this;
        }

        public Builder withSku(String skuToUse) {
            this.sku = skuToUse;
            return this;
        }

        public GetProductRequest build() {
            return new GetProductRequest(this);
        }

    }
}
