package com.shopify.inventoryservice.converters;

import com.shopify.inventoryservice.dynamodb.table.Company;
import com.shopify.inventoryservice.models.CompanyModel;

import javax.inject.Inject;

public class CompanyModelConverter {

    @Inject
    public CompanyModelConverter() {
    }

    public CompanyModel toCompanyModel(Company company) {
        CompanyModel companyModel = CompanyModel.builder()
                .withCompanyName(company.getCompanyName())
                .withEmailAddress(company.getEmailAddress())
                .withCreatedAt(company.getCreatedAt())
                .build();

        return companyModel;
    }
}
