package com.shopify.inventoryservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.shopify.inventoryservice.converters.CompanyModelConverter;
import com.shopify.inventoryservice.dynamodb.CompanyDao;
import com.shopify.inventoryservice.dynamodb.table.Company;
import com.shopify.inventoryservice.exceptions.CompanyNameAlreadyExistsException;
import com.shopify.inventoryservice.exceptions.CompanyNotFoundException;
import com.shopify.inventoryservice.exceptions.InvalidAttributeValueException;
import com.shopify.inventoryservice.models.CompanyModel;
import com.shopify.inventoryservice.models.request.CreateCompanyRequest;
import com.shopify.inventoryservice.models.response.CreateCompanyResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;

import java.sql.Timestamp;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CreateCompanyActivityTest {

    private final String companyName = "Dunder Mifflin";
    private final String validEmailAddress = "dundermifflin@email.com";
    private final String validPassword = "DunderMifflin@345";
    private final String invalidEmailAddress = "dundermifflin@email";
    private final String invalidPassword = "DunderMifflin";

    @Mock
    private CompanyDao companyDao;
    @Mock
    private CompanyModelConverter companyModelConverter;
    @Mock
    private Context context;

    private CreateCompanyActivity createCompanyActivity;
    private LambdaLogger lambdaLogger;

    @BeforeEach
    public void setup() {
        initMocks(this);
        createCompanyActivity = new CreateCompanyActivity(companyDao, companyModelConverter);
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
    public void handleRequest_createValidCompany_returnsValidCreateCompanyResponse() {
        CreateCompanyRequest createCompanyRequest = CreateCompanyRequest.builder()
                .withCompanyName(companyName)
                .withEmailAddress(validEmailAddress)
                .withPassword(validPassword)
                .build();

        CompanyModel companyModel = CompanyModel.builder()
                .withCompanyName(companyName)
                .withEmailAddress(validEmailAddress)
                .withCreatedAt(new Timestamp(System.currentTimeMillis()).toString())
                .build();

        when(companyDao.saveCompany(any(Company.class))).then(AdditionalAnswers.returnsFirstArg());
        when(context.getLogger()).thenReturn(lambdaLogger);
        when(companyDao.getCompany(companyName)).thenThrow(CompanyNotFoundException.class);
        when(companyModelConverter.toCompanyModel(any(Company.class))).thenReturn(companyModel);

        CreateCompanyResponse createCompanyResponse = createCompanyActivity.handleRequest(createCompanyRequest, context);

        assertEquals(companyName, createCompanyResponse.getCompanyModel().getCompanyName(),
                "Expected company name to be " + companyName);
        assertEquals(validEmailAddress, createCompanyResponse.getCompanyModel().getEmailAddress(),
                "Expected email address to be " + validEmailAddress);

    }

    @Test
    public void handleRequest_createsCompanyNameAlreadyExists_throwsCompanyNameAlreadyExistsException() {
        CreateCompanyRequest createCompanyRequest = CreateCompanyRequest.builder()
                .withCompanyName(companyName)
                .withEmailAddress(validEmailAddress)
                .withPassword(validPassword)
                .build();

        when(companyDao.getCompany(companyName)).thenThrow(CompanyNameAlreadyExistsException.class);
        when(context.getLogger()).thenReturn(lambdaLogger);

        assertThrows(CompanyNameAlreadyExistsException.class, () ->
                createCompanyActivity.handleRequest(createCompanyRequest, context));

    }

    @Test
    public void handleRequest_createsCompanyWithInvalidEmailAddress_throwsInvalidAttributeValueException() {
        CreateCompanyRequest createCompanyRequest = CreateCompanyRequest.builder()
                .withCompanyName(companyName)
                .withEmailAddress(invalidEmailAddress)
                .withPassword(validPassword)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);

        assertThrows(InvalidAttributeValueException.class, () ->
                createCompanyActivity.handleRequest(createCompanyRequest, context));
    }

    @Test
    public void handleRequest_createsCompanyWithInvalidPassword_throwsInvalidAttributeValueException() {
        CreateCompanyRequest createCompanyRequest = CreateCompanyRequest.builder()
                .withCompanyName(companyName)
                .withEmailAddress(validEmailAddress)
                .withPassword(invalidPassword)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);

        assertThrows(InvalidAttributeValueException.class, () ->
                createCompanyActivity.handleRequest(createCompanyRequest, context));
    }


}
