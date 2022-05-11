package com.shopify.inventoryservice.models;

public class CompanyModel {
    private String companyName;
    private String createdAt;
    private String emailAddress;

    public CompanyModel() {
    }

    public CompanyModel(Builder builder) {
        this.companyName = builder.companyName;
        this.createdAt = builder.createdAt;
        this.emailAddress = builder.emailAddress;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String companyName;
        private String createdAt;
        private String emailAddress;

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

        public CompanyModel build() {
            return new CompanyModel(this);
        }
    }
}
