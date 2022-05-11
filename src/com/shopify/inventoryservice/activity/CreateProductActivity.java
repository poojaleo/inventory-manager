package com.shopify.inventoryservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.shopify.inventoryservice.converters.ProductModelConverter;
import com.shopify.inventoryservice.dynamodb.ProductDao;
import com.shopify.inventoryservice.dynamodb.table.Product;
import com.shopify.inventoryservice.exceptions.InvalidAttributeValueException;
import com.shopify.inventoryservice.exceptions.ProductAlreadyExistsException;
import com.shopify.inventoryservice.exceptions.ProductNotFoundException;
import com.shopify.inventoryservice.models.ProductModel;
import com.shopify.inventoryservice.models.request.CreateProductRequest;
import com.shopify.inventoryservice.models.response.CreateProductResponse;

import javax.inject.Inject;

public class CreateProductActivity implements RequestHandler<CreateProductRequest, CreateProductResponse> {
    private final ProductDao productDao;
    private final ProductModelConverter productModelConverter;

    @Inject
    public CreateProductActivity(ProductDao productDao, ProductModelConverter productModelConverter) {
        this.productDao = productDao;
        this.productModelConverter = productModelConverter;
    }

    @Override
    public CreateProductResponse handleRequest(CreateProductRequest createProductRequest, Context context)
            throws InvalidAttributeValueException {
        LambdaLogger lambdaLogger = context.getLogger();
        lambdaLogger.log("Received Create Product Request for company:" + createProductRequest.getCompanyName());

        if(createProductRequest.getQuantity() < 0)
            throw new InvalidAttributeValueException("Product Quantity cannot be less than 0");

        if(createProductRequest.getCost().doubleValue() < 0)
            throw new InvalidAttributeValueException("Product Cost cannot be less than 0");

        /*
        Company companyExistCheck = null;

        try {
            companyExistCheck = companyDao.getCompany(createCompanyRequest.getCompanyName());
        } catch (CompanyNotFoundException exception) {
            lambdaLogger.log("Username does not exist. New company creation in progress");
        }

        if(companyExistCheck != null) {
            throw new CompanyNameAlreadyExistsException(
                    String.format("Company with companyName: %s already exists", createCompanyRequest.getCompanyName()));
        }
         */

        Product productExistCheck = null;

        try {
            productExistCheck = productDao.getProductActiveAndInactive(createProductRequest.getCompanyName(), createProductRequest.getSku());
        } catch (ProductNotFoundException exception) {
            lambdaLogger.log("Product does not exist. New Product creation in progress");
        }

        if(productExistCheck != null) {
            throw new ProductAlreadyExistsException(String.format("SkU with skuId: %s already exists",
                    createProductRequest.getSku()));
        }


        Product product = Product.builder()
                .withCompanyName(createProductRequest.getCompanyName())
                .withSku(createProductRequest.getSku())
                .withName(createProductRequest.getName())
                .withQuantity(createProductRequest.getQuantity())
                .withCost(createProductRequest.getCost())
                .withIsActive(true)
                .build();

        if(createProductRequest.getDescription() != null)
            product.setDescription(createProductRequest.getDescription());

        Product productSaved = productDao.saveProduct(product);
        ProductModel productModel = productModelConverter.toProductModel(productSaved);

        return CreateProductResponse.builder()
                .withProductModel(productModel)
                .build();
    }
}
