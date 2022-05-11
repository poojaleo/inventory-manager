package com.shopify.inventoryservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.shopify.inventoryservice.converters.ProductModelConverter;
import com.shopify.inventoryservice.dynamodb.ProductDao;
import com.shopify.inventoryservice.dynamodb.table.Product;
import com.shopify.inventoryservice.exceptions.ProductNotFoundException;
import com.shopify.inventoryservice.models.ProductModel;
import com.shopify.inventoryservice.models.request.DeleteProductRequest;
import com.shopify.inventoryservice.models.response.DeleteProductResponse;

import javax.inject.Inject;

public class DeleteProductActivity implements RequestHandler<DeleteProductRequest, DeleteProductResponse> {
    public final ProductDao productDao;
    public ProductModelConverter productModelConverter;

    @Inject
    public DeleteProductActivity(ProductDao productDao, ProductModelConverter productModelConverter) {
        this.productDao = productDao;
        this.productModelConverter = productModelConverter;
    }

    @Override
    public DeleteProductResponse handleRequest(DeleteProductRequest deleteProductRequest, Context context)
            throws ProductNotFoundException {
        LambdaLogger lambdaLogger = context.getLogger();
        lambdaLogger.log(String.format("Received Delete Product request for company: %s and sku: %s ",
                deleteProductRequest.getCompanyName(), deleteProductRequest.getSku()));

        Product product;

        try {
            product = productDao.getActiveProduct(deleteProductRequest.getCompanyName(), deleteProductRequest.getSku());
        } catch (ProductNotFoundException productNotFoundException) {
            throw new ProductNotFoundException(String.format("ProductNotFound: No active product found for SKU: %s",
                    deleteProductRequest.getSku()));
        }

        product.setActive(false);
        if(deleteProductRequest.getDeleteComments() != null)
            product.setDeleteComment(deleteProductRequest.getDeleteComments());

        productDao.saveProduct(product);

        ProductModel productModel = productModelConverter.toProductModel(product);

        return DeleteProductResponse.builder()
                .withProductModel(productModel)
                .build();

    }
}
