package com.shopify.inventoryservice.models.request;

import java.util.Objects;

public class DeleteProductRequest {
    private String companyName;
    private String sku;
    private String deleteComments;

    public DeleteProductRequest() {
    }

    public DeleteProductRequest(Builder builder) {
        this.companyName = builder.companyName;
        this.sku = builder.sku;
        this.deleteComments = builder.deleteComments;
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

    public String getDeleteComments() {
        return deleteComments;
    }

    public void setDeleteComments(String deleteComments) {
        this.deleteComments = deleteComments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeleteProductRequest that = (DeleteProductRequest) o;
        return companyName.equals(that.companyName) && sku.equals(that.sku);
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
        private String deleteComments;

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

        public Builder withDeleteComments(String deleteCommentsToUse) {
            this.deleteComments = deleteCommentsToUse;
            return this;
        }

        public DeleteProductRequest build() {
            return new DeleteProductRequest(this);
        }

    }

}
