package com.shopify.inventoryservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.shopify.inventoryservice.dependency.DaggerServiceComponent;
import com.shopify.inventoryservice.dependency.ServiceComponent;
import com.shopify.inventoryservice.models.request.CreateProductRequest;
import com.shopify.inventoryservice.models.response.CreateProductResponse;

public class CreateProductActivityProvider implements RequestHandler<CreateProductRequest, CreateProductResponse> {
    public CreateProductActivityProvider() {
    }

    @Override
    public CreateProductResponse handleRequest(CreateProductRequest createProductRequest, Context context) {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideCreateProductActivity().handleRequest(createProductRequest, context);
    }
}
