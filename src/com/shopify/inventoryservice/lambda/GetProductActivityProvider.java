package com.shopify.inventoryservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.shopify.inventoryservice.dependency.DaggerServiceComponent;
import com.shopify.inventoryservice.dependency.ServiceComponent;
import com.shopify.inventoryservice.models.request.GetProductRequest;
import com.shopify.inventoryservice.models.response.GetProductResponse;

public class GetProductActivityProvider implements RequestHandler<GetProductRequest, GetProductResponse> {
    public GetProductActivityProvider() {
    }

    @Override
    public GetProductResponse handleRequest(GetProductRequest getProductRequest, Context context) {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideGetProductActivity().handleRequest(getProductRequest, context);
    }
}
