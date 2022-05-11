package com.shopify.inventoryservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.shopify.inventoryservice.converters.ProductModelConverter;
import com.shopify.inventoryservice.dynamodb.ProductDao;
import com.shopify.inventoryservice.dynamodb.table.Product;
import com.shopify.inventoryservice.exceptions.ProductNotFoundException;
import com.shopify.inventoryservice.models.ProductModel;
import com.shopify.inventoryservice.models.request.UndeleteProductRequest;
import com.shopify.inventoryservice.models.response.DeleteProductResponse;
import com.shopify.inventoryservice.models.response.UndeleteProductResponse;

import javax.inject.Inject;

public class UndeleteProductActivity implements RequestHandler<UndeleteProductRequest, UndeleteProductResponse> {
    public final ProductDao productDao;
    public ProductModelConverter productModelConverter;

    @Inject
    public UndeleteProductActivity(ProductDao productDao, ProductModelConverter productModelConverter) {
        this.productDao = productDao;
        this.productModelConverter = productModelConverter;
    }

    @Override
    public UndeleteProductResponse handleRequest(UndeleteProductRequest undeleteProductRequest, Context context)
            throws ProductNotFoundException {
        LambdaLogger lambdaLogger = context.getLogger();
        lambdaLogger.log(String.format("Received Delete Product request for company: %s and sku: %s ",
                undeleteProductRequest.getCompanyName(), undeleteProductRequest.getSku()));

        Product product;

        try {
           product = productDao.getInActiveProduct(undeleteProductRequest.getCompanyName(), undeleteProductRequest.getSku());
        } catch (ProductNotFoundException productNotFoundException) {
            throw new ProductNotFoundException(String.format("ProductNotFound: No inactive product found for SKU: %s",
                    undeleteProductRequest.getSku()));
        }

        product.setActive(true);
        product.setDeleteComment("");

        productDao.saveProduct(product);

        ProductModel productModel = productModelConverter.toProductModel(product);

        return UndeleteProductResponse.builder()
                .withProductModel(productModel)
                .build();

    }
}
