package com.shopify.inventoryservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.shopify.inventoryservice.converters.ProductModelConverter;
import com.shopify.inventoryservice.dynamodb.ProductDao;
import com.shopify.inventoryservice.dynamodb.table.Product;
import com.shopify.inventoryservice.models.ProductModel;
import com.shopify.inventoryservice.models.request.GetAllActiveProductRequest;
import com.shopify.inventoryservice.models.response.GetAllActiveProductResponse;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GetAllActiveProductsActivity implements RequestHandler<GetAllActiveProductRequest, GetAllActiveProductResponse> {
    public final ProductDao productDao;
    public ProductModelConverter productModelConverter;

    @Inject
    public GetAllActiveProductsActivity(ProductDao productDao, ProductModelConverter productModelConverter) {
        this.productDao = productDao;
        this.productModelConverter = productModelConverter;
    }

    @Override
    public GetAllActiveProductResponse handleRequest(GetAllActiveProductRequest getAllActiveProductRequest,
                                                     Context context) {
        LambdaLogger lambdaLogger = context.getLogger();
        lambdaLogger.log(String.format("Received Get Product request for company: %s ",
                getAllActiveProductRequest.getCompanyName()));

        List<Product> activeProductList = productDao.getAllActiveProducts(getAllActiveProductRequest.getCompanyName());

        List<ProductModel> activeProductModels = new ArrayList<>();

        if(activeProductList != null) {
            activeProductModels = activeProductList.stream()
                    .map(product -> productModelConverter.toProductModel(product))
                    .collect(Collectors.toList());
        }

        return GetAllActiveProductResponse.builder()
                .withActiveProductsList(activeProductModels)
                .build();
    }
}
