package com.shopify.inventoryservice.models.request;

public class GetCompanyRequest {
    private String companyName;
    private String password;

    public GetCompanyRequest() {
    }

    public GetCompanyRequest(String companyName, String password) {
        this.companyName = companyName;
        this.password = password;
    }

    public GetCompanyRequest(Builder builder) {
        this.companyName = builder.companyName;
        this.password = builder.password;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String companyName;
        private String password;

        public Builder withCompanyName(String companyNameToUse) {
            this.companyName = companyNameToUse;
            return this;
        }

        public Builder withPassword(String passwordToUse) {
            this.password = passwordToUse;
            return this;
        }

        public GetCompanyRequest build() {
            return new GetCompanyRequest(this);
        }
    }
}
