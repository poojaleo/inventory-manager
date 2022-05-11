package com.shopify.inventoryservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.shopify.inventoryservice.converters.ProductModelConverter;
import com.shopify.inventoryservice.dynamodb.ProductDao;
import com.shopify.inventoryservice.dynamodb.table.Product;
import com.shopify.inventoryservice.models.ProductModel;
import com.shopify.inventoryservice.models.request.GetAllInactiveProductRequest;
import com.shopify.inventoryservice.models.response.GetAllInactiveProductResponse;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GetAllInactiveProductsActivity implements RequestHandler<GetAllInactiveProductRequest, GetAllInactiveProductResponse> {
    public final ProductDao productDao;
    public ProductModelConverter productModelConverter;

    @Inject
    public GetAllInactiveProductsActivity(ProductDao productDao, ProductModelConverter productModelConverter) {
        this.productDao = productDao;
        this.productModelConverter = productModelConverter;
    }

    @Override
    public GetAllInactiveProductResponse handleRequest(GetAllInactiveProductRequest getAllInactiveProductRequest, Context context) {
        LambdaLogger lambdaLogger = context.getLogger();
        lambdaLogger.log(String.format("Received Get Product request for company: %s ",
                getAllInactiveProductRequest.getCompanyName()));

        List<Product> inactiveProductList = productDao.getAllInactiveProducts(getAllInactiveProductRequest.getCompanyName());

        List<ProductModel> inactiveProductModels = new ArrayList<>();

        if(inactiveProductList != null) {
            inactiveProductModels = inactiveProductList.stream()
                    .map(product -> productModelConverter.toProductModel(product))
                    .collect(Collectors.toList());
        }

        return GetAllInactiveProductResponse.builder()
                .withInActiveProductsList(inactiveProductModels)
                .build();
    }
}
