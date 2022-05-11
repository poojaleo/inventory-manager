package com.shopify.inventoryservice.models.request;

import java.math.BigDecimal;
import java.util.Objects;

public class CreateProductRequest {
    private String companyName;
    private String sku;
    private String name;
    private String description;
    private int quantity;
    private BigDecimal cost;

    public CreateProductRequest() {
    }

    public CreateProductRequest(String companyName, String sku, String name, String description, int quantity, BigDecimal cost) {
        this.companyName = companyName;
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.cost = cost;
    }

    public CreateProductRequest(String companyName, String sku, String name, int quantity, BigDecimal cost) {
        this.companyName = companyName;
        this.sku = sku;
        this.name = name;
        this.quantity = quantity;
        this.cost = cost;
    }

    public CreateProductRequest(Builder builder) {
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
        CreateProductRequest that = (CreateProductRequest) o;
        return Objects.equals(companyName, that.companyName) && Objects.equals(sku, that.sku) && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyName, sku, name, description);
    }

    public static Builder builder() {return new Builder();}

    public static final class Builder {
        private String companyName;
        private String sku;
        private String name;
        private String description;
        private int quantity;
        private BigDecimal cost;

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


        public CreateProductRequest build() {
            return new CreateProductRequest(this);
        }
    }


}
