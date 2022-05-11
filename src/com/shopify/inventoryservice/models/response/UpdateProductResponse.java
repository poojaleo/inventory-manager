package com.shopify.inventoryservice.models.response;

import com.shopify.inventoryservice.models.ProductModel;

public class UpdateProductResponse {
    private ProductModel productModel;

    public UpdateProductResponse(Builder builder) {
        this.productModel = builder.productModel;
    }

    public ProductModel getProductModel() {
        return productModel;
    }

    public void setProductModel(ProductModel productModel) {
        this.productModel = productModel;
    }

    public static Builder builder() { return new Builder();}

    public static final class Builder {
        private ProductModel productModel;

        public Builder withProductModel(ProductModel productModelToUse) {
            this.productModel = productModelToUse;
            return this;
        }

        public UpdateProductResponse build() {
            return new UpdateProductResponse(this);
        }
    }
}
