package com.shopify.inventoryservice.models.response;

import com.shopify.inventoryservice.models.ProductModel;

import java.util.List;
import java.util.Objects;

public class GetAllInactiveProductResponse {
    private List<ProductModel> inactiveProductsList;

    public GetAllInactiveProductResponse(Builder builder) {
        this.inactiveProductsList = builder.inactiveProductsList;
    }

    public List<ProductModel> getInactiveProductsList() {
        return inactiveProductsList;
    }

    public void setInactiveProductsList(List<ProductModel> inactiveProductsList) {
        this.inactiveProductsList = inactiveProductsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetAllInactiveProductResponse that = (GetAllInactiveProductResponse) o;
        return inactiveProductsList.equals(that.inactiveProductsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inactiveProductsList);
    }

    public static Builder builder() { return new Builder();}

    public static final class Builder {
        private List<ProductModel> inactiveProductsList;

        public Builder withInActiveProductsList(List<ProductModel> inactiveProductsListToUse) {
            this.inactiveProductsList = inactiveProductsListToUse;
            return this;
        }

        public GetAllInactiveProductResponse build() {
            return new GetAllInactiveProductResponse(this);
        }

    }
}
