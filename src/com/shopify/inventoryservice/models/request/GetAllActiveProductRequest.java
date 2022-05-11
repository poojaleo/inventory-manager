package com.shopify.inventoryservice.models.request;

import java.util.Objects;

public class GetAllActiveProductRequest {
    private String companyName;

    public GetAllActiveProductRequest() {
    }

    public GetAllActiveProductRequest(Builder builder) {
        this.companyName = builder.companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetAllActiveProductRequest that = (GetAllActiveProductRequest) o;
        return companyName.equals(that.companyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyName);
    }

    public static Builder builder() { return new Builder();}

    public static final class Builder {
        private String companyName;

        public Builder() {
        }

        public Builder withCompanyName(String companyNameToUse) {
            this.companyName = companyNameToUse;
            return this;
        }

        public GetAllActiveProductRequest build() {
            return new GetAllActiveProductRequest(this);
        }
    }


}
