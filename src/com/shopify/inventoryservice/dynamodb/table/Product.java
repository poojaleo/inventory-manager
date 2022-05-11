package com.shopify.inventoryservice.dynamodb.table;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.math.BigDecimal;
import java.util.Objects;

@DynamoDBTable(tableName = "Products")
public class Product {

    public static final String IS_ACTIVE_INDEX = "isActiveIndex";

    private String companyName;
    private String sku;
    private String name;
    private String description;
    private int quantity;
    private BigDecimal cost;
    private boolean isActive;
    private String deleteComment;

    public Product() {
    }

    public Product(Builder builder) {
        this.companyName = builder.companyName;
        this.sku = builder.sku;
        this.name = builder.name;
        this.description = builder.description;
        this.quantity = builder.quantity;
        this.cost = builder.cost;
        this.isActive = builder.isActive;
        this.deleteComment = builder.deleteComment;
    }

    @DynamoDBIndexHashKey(attributeName = "companyName", globalSecondaryIndexName = IS_ACTIVE_INDEX)
    @DynamoDBHashKey(attributeName = "companyName")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @DynamoDBRangeKey(attributeName = "sku")
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
    @DynamoDBIndexRangeKey(attributeName = "isActive", globalSecondaryIndexName = IS_ACTIVE_INDEX)
    @DynamoDBAttribute(attributeName = "isActive")
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @DynamoDBAttribute(attributeName = "quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.N)
    @DynamoDBAttribute(attributeName = "cost")
    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }


    @DynamoDBAttribute(attributeName = "deleteComment")
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
        Product product = (Product) o;
        return Objects.equals(sku, product.sku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sku);
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

        public Product build() {
            return new Product(this);
        }

    }
}
