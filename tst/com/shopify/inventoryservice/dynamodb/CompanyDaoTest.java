package com.shopify.inventoryservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.shopify.inventoryservice.dynamodb.table.Company;
import com.shopify.inventoryservice.exceptions.CompanyNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CompanyDaoTest {
    private final String companyName = "Dunder Mifflin";
    private final String validEmailAddress = "dundermifflin@email.com";
    private final String validPassword = "DunderMifflin@345";
    private final String createdAt = new Timestamp(System.currentTimeMillis()).toString();

    private CompanyDao companyDao;

    @Mock
    private DynamoDBMapper mapper;

    @BeforeEach
    public void setup() {
        initMocks(this);
        companyDao = new CompanyDao(mapper);
    }

    @Test
    public void getCompany_validCompanyName_returnsCompany() {
        Company company = Company.builder()
                .withCompanyName(companyName)
                .withEmailAddress(validEmailAddress)
                .withPassword(validPassword)
                .withCreatedAt(createdAt)
                .build();

        when(mapper.load(Company.class, companyName)).thenReturn(company);

        Company companyReturned = companyDao.getCompany(companyName);

        assertEquals(companyName, companyReturned.getCompanyName(),
                "Expected company name to be " + companyName);
        assertEquals(validEmailAddress, companyReturned.getEmailAddress(),
                "Expected email address to be " + validEmailAddress);
        assertEquals(createdAt, companyReturned.getCreatedAt(),
                "Expected created at time to be " + createdAt);
        assertEquals(validPassword, companyReturned.getPassword(),
                "Expected password to be " + validPassword);
    }

    @Test
    public void getCompany_companyNameNotInDatabase_throwsCompanyNotFoundException() {
        when(mapper.load(Company.class, companyName)).thenReturn(null);

       assertThrows(CompanyNotFoundException.class, () -> companyDao.getCompany(companyName));
    }

    @Test
    public void saveCompany_validCompany_returnsCompany() {
        Company company = Company.builder()
                .withCompanyName(companyName)
                .withEmailAddress(validEmailAddress)
                .withPassword(validPassword)
                .withCreatedAt(createdAt)
                .build();

        Company companyReturned = companyDao.saveCompany(company);
        assertEquals(companyName, companyReturned.getCompanyName(),
                "Expected company name to be " + companyName);
        assertEquals(validEmailAddress, companyReturned.getEmailAddress(),
                "Expected email address to be " + validEmailAddress);
        assertEquals(createdAt, companyReturned.getCreatedAt(),
                "Expected created at time to be " + createdAt);
        assertEquals(validPassword, companyReturned.getPassword(),
                "Expected password to be " + validPassword);
        verify(mapper, times(1)).save(company);
    }


}
