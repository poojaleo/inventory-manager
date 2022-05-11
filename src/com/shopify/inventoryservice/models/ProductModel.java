package com.shopify.inventoryservice.models;


import java.math.BigDecimal;
import java.util.Objects;

public class ProductModel {
    private String companyName;
    private String sku;
    private String name;
    private String description;
    private int quantity;
    private BigDecimal cost;
    private boolean isActive;
    private String deleteComment;

    public ProductModel() {
    }

    public ProductModel(Builder builder) {
        this.companyName = builder.companyName;
        this.sku = builder.sku;
        this.name = builder.name;
        this.description = builder.description;
        this.quantity = builder.quantity;
        this.cost = builder.cost;
        this.isActive = builder.isActive;
        this.deleteComment = builder.deleteComment;

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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getDeleteComment() {
        return deleteComment;
    }

    public void setDeleteComment(String deleteComment) {
        this.deleteComment = deleteComment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductModel that = (ProductModel) o;
        return Objects.equals(sku, that.sku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sku);
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "sku='" + sku + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", cost=" + cost +
                ", isActive=" + isActive +
                ", deleteComment='" + deleteComment + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String companyName;
        private String sku;
        private String name;
        private String description;
        private int quantity;
        private BigDecimal cost;
        private boolean isActive;
        private String deleteComment;

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

        public Builder withIsActive(Boolean isActiveToUse) {
            this.isActive = isActiveToUse;
            return this;
        }

        public Builder withDeleteComment(String deleteCommentToUse) {
            this.deleteComment = deleteCommentToUse;
            return this;
        }

        public ProductModel build() {
            return new ProductModel(this);
        }

    }

}
