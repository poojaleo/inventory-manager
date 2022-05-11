package com.shopify.inventoryservice.models.request;

import java.math.BigDecimal;
import java.util.Objects;

public class UpdateProductRequest {
    private String companyName;
    private String sku;
    private String name;
    private String description;
    private int quantity;
    private BigDecimal cost;

    public UpdateProductRequest() {
    }

    public UpdateProductRequest(Builder builder) {
        this.companyName = builder.companyName;
        this.sku = builder.sku;
        this.name = builder.name;
        this.description = builder.description;
        this.quantity = builder.quantity;
        this.cost = builder.cost;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateProductRequest that = (UpdateProductRequest) o;
        return quantity == that.quantity && companyName.equals(that.companyName) && sku.equals(that.sku) && name.equals(that.name) && description.equals(that.description) && cost.equals(that.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyName, sku, name, description, quantity, cost);
    }

    public static Builder builder() { return new Builder();}

    public static final class Builder {
        private String companyName;
        private String sku;
        private String name;
        private String description;
        private int quantity;
        private BigDecimal cost;

        public Builder withCompanyName(String companyNameToUse) {
            this.companyName = companyNameToUse;
            return this;
        }

        public Builder withSku(String skuToUse) {
            this.sku = skuToUse;
            return this;
        }

        public Builder withName(String nameToUse) {
            this.name = nameToUse;
            return this;
        }

        public Builder withDescription(String descriptionToUse) {
            this.description = descriptionToUse;
            return this;
        }

        public Builder withQuantity(int quantityToUse) {
            this.quantity = quantityToUse;
            return this;
        }

        public Builder withCost(BigDecimal costToUse) {
            this.cost = costToUse;
            return this;
        }

        public UpdateProductRequest build() {
            return new UpdateProductRequest(this);
        }
    }
}
