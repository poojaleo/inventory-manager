package com.shopify.inventoryservice.models.request;


public class CreateCompanyRequest {
    private String companyName;
    private String emailAddress;
    private String password;

    public CreateCompanyRequest(String companyName, String emailAddress, String password) {
        this.companyName = companyName;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public CreateCompanyRequest() {
    }

    public CreateCompanyRequest(Builder builder) {
        this.companyName = builder.companyName;
        this.emailAddress = builder.emailAddress;
        this.password = builder.password;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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
        private String emailAddress;
        private String password;

        public Builder() {
        }

        public Builder withCompanyName(String companyNameToUse) {
            this.companyName = companyNameToUse;
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

        public CreateCompanyRequest build() {
            return new CreateCompanyRequest(this);
        }
    }

}
