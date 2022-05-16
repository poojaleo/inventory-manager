package com.shopify.inventoryservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.shopify.inventoryservice.converters.CompanyModelConverter;
import com.shopify.inventoryservice.dynamodb.CompanyDao;
import com.shopify.inventoryservice.dynamodb.table.Company;
import com.shopify.inventoryservice.exceptions.CompanyNotFoundException;
import com.shopify.inventoryservice.models.CompanyModel;
import com.shopify.inventoryservice.models.request.GetCompanyRequest;
import com.shopify.inventoryservice.models.response.GetCompanyResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetCompanyActivityTest {
    private final String companyName = "Dunder Mifflin";
    private final String validEmailAddress = "dundermifflin@email.com";
    private final String validPassword = "DunderMifflin@345";
    private final String companyNameNotInDatabase = "Mifflin";
    private final String createdAt = new Timestamp(System.currentTimeMillis()).toString();


    @Mock
    private CompanyDao companyDao;
    @Mock
    private CompanyModelConverter companyModelConverter;
    @Mock
    private Context context;

    private GetCompanyActivity getCompanyActivity;
    private LambdaLogger lambdaLogger;

    @BeforeEach
    public void setup() {
        initMocks(this);
        getCompanyActivity = new GetCompanyActivity(companyDao, companyModelConverter);
        lambdaLogger = new LambdaLogger() {
            @Override
            public void log(String message) {
                System.out.println(message);
            }

            @Override
            public void log(byte[] message) {
                System.out.println(message);
            }
        };
    }

    @Test
    public void handleRequest_companyInDatabase_returnsGetCompanyResponse() {
        Company company = Company.builder()
                .withCompanyName(companyName)
                .withEmailAddress(validEmailAddress)
                .withPassword(validPassword)
                .withCreatedAt(createdAt)
                .build();

        CompanyModel companyModel = CompanyModel.builder()
                .withCompanyName(companyName)
                .withCreatedAt(createdAt)
                .withEmailAddress(validEmailAddress)
                .build();

        GetCompanyRequest getCompanyRequest = GetCompanyRequest.builder()
                .withCompanyName(companyName)
                .withPassword(validPassword)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(companyDao.getCompany(companyName)).thenReturn(company);
        when(companyModelConverter.toCompanyModel(company)).thenReturn(companyModel);

        GetCompanyResponse getCompanyResponse = getCompanyActivity.handleRequest(getCompanyRequest, context);

        assertEquals(companyName, getCompanyResponse.getCompanyModel().getCompanyName(),
                "Expected company name to be " + companyName);
        assertEquals(validEmailAddress, getCompanyResponse.getCompanyModel().getEmailAddress(),
                "Expected email address to be " + validEmailAddress);
        assertEquals(createdAt, getCompanyResponse.getCompanyModel().getCreatedAt(),
                "Expected created at time to be " + createdAt);


    }

    @Test
    public void handleRequest_companyNotInDatabase_throwsCompanyNotFoundException() {
        GetCompanyRequest getCompanyRequest = GetCompanyRequest.builder()
                .withCompanyName(companyNameNotInDatabase)
                .withPassword(validPassword)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(companyDao.getCompany(companyNameNotInDatabase)).thenThrow(CompanyNotFoundException.class);

        assertThrows(CompanyNotFoundException.class, () -> getCompanyActivity.handleRequest(getCompanyRequest, context));
    }
}
