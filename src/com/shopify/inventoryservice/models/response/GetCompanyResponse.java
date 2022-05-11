package com.shopify.inventoryservice.models.response;

import com.shopify.inventoryservice.models.CompanyModel;

public class GetCompanyResponse {
    private CompanyModel companyModel;

    public GetCompanyResponse(Builder builder) {
        this.companyModel = builder.companyModel;
    }

    public CompanyModel getCompanyModel() {
        return companyModel;
    }

    public void setCompanyModel(CompanyModel companyModel) {
        this.companyModel = companyModel;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private CompanyModel companyModel;

        public Builder withCompanyModel(CompanyModel companyModelToUse) {
            this.companyModel = companyModelToUse;
            return this;
        }

        public GetCompanyResponse build() {
            return new GetCompanyResponse(this);
        }
    }
}
