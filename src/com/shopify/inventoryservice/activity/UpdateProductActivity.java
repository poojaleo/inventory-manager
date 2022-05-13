package com.shopify.inventoryservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.shopify.inventoryservice.converters.ProductModelConverter;
import com.shopify.inventoryservice.dynamodb.ProductDao;
import com.shopify.inventoryservice.dynamodb.table.Product;
import com.shopify.inventoryservice.exceptions.InvalidAttributeValueException;
import com.shopify.inventoryservice.exceptions.ProductNotFoundException;
import com.shopify.inventoryservice.models.ProductModel;
import com.shopify.inventoryservice.models.request.UpdateProductRequest;
import com.shopify.inventoryservice.models.response.UpdateProductResponse;

import javax.inject.Inject;

public class UpdateProductActivity implements RequestHandler<UpdateProductRequest, UpdateProductResponse> {
    public final ProductDao productDao;
    public ProductModelConverter productModelConverter;

    @Inject
    public UpdateProductActivity(ProductDao productDao, ProductModelConverter productModelConverter) {
        this.productDao = productDao;
        this.productModelConverter = productModelConverter;
    }

    @Override
    public UpdateProductResponse handleRequest(UpdateProductRequest updateProductRequest, Context context)
            throws ProductNotFoundException, InvalidAttributeValueException {
        LambdaLogger lambdaLogger = context.getLogger();
        lambdaLogger.log(String.format("Received Update Product request for company: %s and sku: %s ",
                updateProductRequest.getCompanyName(), updateProductRequest.getSku()));

        Product product;

        try {
            product = productDao.getActiveProduct(updateProductRequest.getCompanyName(), updateProductRequest.getSku());
        } catch (ProductNotFoundException productNotFoundException) {
            throw  productNotFoundException;
        }

        if(updateProductRequest.getQuantity() < 0)
            throw new InvalidAttributeValueException("Invalid: Product Quantity cannot be less than 0");

        if(updateProductRequest.getCost().doubleValue() < 0)
            throw new InvalidAttributeValueException("Invalid: Product Cost cannot be less than 0");

        product.setName(updateProductRequest.getName());
        product.setDescription(updateProductRequest.getDescription());
        product.setCost(updateProductRequest.getCost());
        product.setQuantity(updateProductRequest.getQuantity());

        Product productSaved = productDao.saveProduct(product);
        ProductModel productModel = productModelConverter.toProductModel(productSaved);

        return UpdateProductResponse.builder()
                .withProductModel(productModel)
                .build();
    }
}
