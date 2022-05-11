package com.shopify.inventoryservice.models.response;

import com.shopify.inventoryservice.dynamodb.table.Product;
import com.shopify.inventoryservice.models.ProductModel;

import java.util.List;

public class GetAllActiveProductResponse {
    private List<ProductModel> activeProductsList;

    public GetAllActiveProductResponse(Builder builder) {
        this.activeProductsList = builder.activeProductsList;
    }

    public List<ProductModel> getActiveProductsList() {
        return activeProductsList;
    }

    public void setActiveProductsList(List<ProductModel> activeProductsList) {
        this.activeProductsList = activeProductsList;
    }

    public static Builder builder() { return new Builder();}

    public static final class Builder {
        private List<ProductModel> activeProductsList;

        public Builder withActiveProductsList(List<ProductModel> activeProductsListToUse) {
            this.activeProductsList = activeProductsListToUse;
            return this;
        }

        public GetAllActiveProductResponse build() {
            return new GetAllActiveProductResponse(this);
        }

    }
}
