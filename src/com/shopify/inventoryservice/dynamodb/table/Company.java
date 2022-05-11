package com.shopify.inventoryservice.dynamodb.table;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "Companies")
public class Company {
    private String companyName;
    private String createdAt;
    private String emailAddress;
    private String password;

    public Company() {
    }

    public Company(Builder builder) {
        this.companyName = builder.companyName;
        this.createdAt = builder.createdAt;
        this.emailAddress = builder.emailAddress;
        this.password = builder.password;
    }

    @DynamoDBHashKey(attributeName = "companyName")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @DynamoDBAttribute
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @DynamoDBAttribute
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @DynamoDBAttribute
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(companyName, company.companyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyName);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String companyName;
        private String createdAt;
        private String emailAddress;
        private String password;

        public Builder() {
        }

        public Builder withCompanyName(String companyNameToUse) {
            this.companyName = companyNameToUse;
            return this;
        }

        public Builder withCreatedAt(String createdAtToUse) {
            this.createdAt = createdAtToUse;
            return this;
        }

        public Builder withEmailAddress(String emailAddressToUse) {
            this.emailAddress = emailAddressToUse;
            return this;
        }

        public Builder withPassword(String passwordToUse) {
            this.password = passwordToUse;
            return this;
        }

        public Company build() {
            return new Company(this);
        }
    }
}
