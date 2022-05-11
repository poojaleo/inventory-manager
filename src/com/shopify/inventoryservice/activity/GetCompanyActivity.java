package com.shopify.inventoryservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.shopify.inventoryservice.converters.CompanyModelConverter;
import com.shopify.inventoryservice.dynamodb.CompanyDao;
import com.shopify.inventoryservice.dynamodb.table.Company;
import com.shopify.inventoryservice.exceptions.CompanyNotFoundException;
import com.shopify.inventoryservice.exceptions.InvalidPasswordException;
import com.shopify.inventoryservice.models.CompanyModel;
import com.shopify.inventoryservice.models.request.GetCompanyRequest;
import com.shopify.inventoryservice.models.response.GetCompanyResponse;

import javax.inject.Inject;

public class GetCompanyActivity implements RequestHandler<GetCompanyRequest, GetCompanyResponse> {
    private final CompanyDao companyDao;
    private CompanyModelConverter companyModelConverter;

    @Inject
    public GetCompanyActivity(CompanyDao companyDao, CompanyModelConverter companyModelConverter) {
        this.companyDao = companyDao;
        this.companyModelConverter = companyModelConverter;
    }

    @Override
    public GetCompanyResponse handleRequest(GetCompanyRequest getCompanyRequest, Context context) throws CompanyNotFoundException {
        LambdaLogger lambdaLogger = context.getLogger();
        lambdaLogger.log("Received Get Company Request for company name: " + getCompanyRequest.getCompanyName());

        Company company;

        try {
            company = companyDao.getCompany(getCompanyRequest.getCompanyName());
        } catch (CompanyNotFoundException companyNotFoundException) {
            throw companyNotFoundException;
        }

        if(!company.getPassword().equals(getCompanyRequest.getPassword())) {
            throw new InvalidPasswordException("Password do not match. Please enter a valid password");
        }

        CompanyModel companyModel = companyModelConverter.toCompanyModel(company);

        return GetCompanyResponse.builder()
                .withCompanyModel(companyModel)
                .build();

    }
}
