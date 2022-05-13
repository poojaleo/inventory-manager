package com.shopify.inventoryservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.shopify.inventoryservice.converters.CompanyModelConverter;
import com.shopify.inventoryservice.dynamodb.CompanyDao;
import com.shopify.inventoryservice.dynamodb.table.Company;
import com.shopify.inventoryservice.exceptions.CompanyNameAlreadyExistsException;
import com.shopify.inventoryservice.exceptions.CompanyNotFoundException;
import com.shopify.inventoryservice.exceptions.InvalidAttributeValueException;
import com.shopify.inventoryservice.models.CompanyModel;
import com.shopify.inventoryservice.models.request.CreateCompanyRequest;
import com.shopify.inventoryservice.models.response.CreateCompanyResponse;
import com.shopify.inventoryservice.utils.InventoryManagerServiceUtility;

import javax.inject.Inject;
import java.sql.Timestamp;

public class CreateCompanyActivity implements RequestHandler<CreateCompanyRequest, CreateCompanyResponse> {

    private final CompanyDao companyDao;
    private CompanyModelConverter companyModelConverter;

    @Inject
    public CreateCompanyActivity(CompanyDao companyDao, CompanyModelConverter companyModelConverter) {
        this.companyDao = companyDao;
        this.companyModelConverter = companyModelConverter;
    }

    @Override
    public CreateCompanyResponse handleRequest(CreateCompanyRequest createCompanyRequest, Context context) {
        LambdaLogger lambdaLogger = context.getLogger();
        lambdaLogger.log("Received Create Company Requests for companyName: " + createCompanyRequest.getCompanyName());

        boolean isValidEmail = InventoryManagerServiceUtility.isValidEmailAddress(createCompanyRequest.getEmailAddress());
        if(!isValidEmail) {
            throw new InvalidAttributeValueException(
                    String.format("Invalid Email Address", createCompanyRequest.getEmailAddress()));
        }

        boolean isValidPassword = InventoryManagerServiceUtility.isValidPassword(createCompanyRequest.getPassword());
        if(!isValidPassword) {
            throw new InvalidAttributeValueException(
                    String.format("Invalid Password", createCompanyRequest.getPassword()));
        }

        Company companyExistCheck = null;

        try {
            companyExistCheck = companyDao.getCompany(createCompanyRequest.getCompanyName());
        } catch (CompanyNotFoundException exception) {
            lambdaLogger.log("CompanyName does not exist. New company creation in progress");
        }

        if(companyExistCheck != null) {
            throw new CompanyNameAlreadyExistsException(
                    String.format("Already Exists: Company with companyName: %s already exists", createCompanyRequest.getCompanyName()));
        }

        Company company = Company.builder()
                .withCompanyName(createCompanyRequest.getCompanyName())
                .withEmailAddress(createCompanyRequest.getEmailAddress())
                .withPassword(createCompanyRequest.getPassword())
                .withCreatedAt(new Timestamp(System.currentTimeMillis()).toString())
                .build();

        Company companySaved = companyDao.saveCompany(company);
        CompanyModel companyModel = companyModelConverter.toCompanyModel(companySaved);

        return CreateCompanyResponse.builder()
                .withCompanyModel(companyModel)
                .build();
    }
}
