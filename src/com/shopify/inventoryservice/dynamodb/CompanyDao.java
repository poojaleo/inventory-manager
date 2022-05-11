package com.shopify.inventoryservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.shopify.inventoryservice.dynamodb.table.Company;
import com.shopify.inventoryservice.exceptions.CompanyNotFoundException;

import javax.inject.Inject;

public class CompanyDao {
    private final DynamoDBMapper mapper;

    @Inject
    public CompanyDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Returns the {@link Company} corresponding to specified compnanyName
     * @param companyName of the company
     * @return Company object if found in the dynamoDb Table
     * @throws CompanyNotFoundException if company with corresponding companyName does not exist
     */

    public Company getCompany(String companyName) throws CompanyNotFoundException {
        Company company = this.mapper.load(Company.class, companyName);

        if(company == null) {
            throw new CompanyNotFoundException(String.format(
                    "CompanyNotFound - Could not find user with company name: %s", companyName));
        }

        return company;
    }

    public Company saveCompany(Company company) {
        this.mapper.save(company);
        return company;
    }

}
