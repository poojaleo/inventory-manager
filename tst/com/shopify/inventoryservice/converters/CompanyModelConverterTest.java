package com.shopify.inventoryservice.converters;

import com.shopify.inventoryservice.dynamodb.table.Company;
import com.shopify.inventoryservice.models.CompanyModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompanyModelConverterTest {
    private final String companyName = "Dunder Mifflin";
    private final String validEmailAddress = "dundermifflin@email.com";
    private final String validPassword = "DunderMifflin@345";
    private final String createdAt = new Timestamp(System.currentTimeMillis()).toString();

    private CompanyModelConverter companyModelConverter;

    @BeforeEach
    public void setup() {
        companyModelConverter = new CompanyModelConverter();
    }

    @Test
    public void toCompanyModel_withCompany_returnsCompanyModel() {
        Company company = Company.builder()
                .withCompanyName(companyName)
                .withEmailAddress(validEmailAddress)
                .withPassword(validPassword)
                .withCreatedAt(createdAt)
                .build();

        CompanyModel companyModel = companyModelConverter.toCompanyModel(company);

        assertEquals(companyName, companyModel.getCompanyName(),
                "Expected company name to be " + companyName);
        assertEquals(validEmailAddress, companyModel.getEmailAddress(),
                "Expected email address to be " + validEmailAddress);
        assertEquals(createdAt, companyModel.getCreatedAt(),
                "Expected createdAt time to be " + createdAt);

    }
}
