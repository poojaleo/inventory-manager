package com.shopify.inventoryservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.shopify.inventoryservice.converters.ProductModelConverter;
import com.shopify.inventoryservice.dynamodb.ProductDao;
import com.shopify.inventoryservice.dynamodb.table.Product;
import com.shopify.inventoryservice.exceptions.ProductNotFoundException;
import com.shopify.inventoryservice.models.ProductModel;
import com.shopify.inventoryservice.models.request.GetProductRequest;
import com.shopify.inventoryservice.models.response.GetProductResponse;

import javax.inject.Inject;

public class GetProductActivity implements RequestHandler<GetProductRequest, GetProductResponse> {
    public final ProductDao productDao;
    public ProductModelConverter productModelConverter;

    @Inject
    public GetProductActivity(ProductDao productDao, ProductModelConverter productModelConverter) {
        this.productDao = productDao;
        this.productModelConverter = productModelConverter;
    }

    @Override
    public GetProductResponse handleRequest(GetProductRequest getProductRequest, Context context)
            throws ProductNotFoundException {
        LambdaLogger lambdaLogger = context.getLogger();
        lambdaLogger.log(String.format("Received Get Product request for company: %s and sku: %s ",
                getProductRequest.getCompanyName(), getProductRequest.getSku()));

        Product product;

        try {
            product = productDao.getActiveProduct(getProductRequest.getCompanyName(), getProductRequest.getSku());
        } catch (ProductNotFoundException productNotFoundException) {
            throw new ProductNotFoundException("ProductNotFound: No active product found for sku: " + getProductRequest.getSku());
        }

        ProductModel productModel = productModelConverter.toProductModel(product);

        return GetProductResponse.builder()
                .withProductModel(productModel)
                .build();
    }
}
