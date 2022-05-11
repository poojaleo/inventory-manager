package com.shopify.inventoryservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.shopify.inventoryservice.dependency.DaggerServiceComponent;
import com.shopify.inventoryservice.dependency.ServiceComponent;
import com.shopify.inventoryservice.models.request.UndeleteProductRequest;
import com.shopify.inventoryservice.models.response.UndeleteProductResponse;

public class UndeleteProductActivityProvider implements RequestHandler<UndeleteProductRequest, UndeleteProductResponse> {
    public UndeleteProductActivityProvider() {
    }

    @Override
    public UndeleteProductResponse handleRequest(UndeleteProductRequest undeleteProductRequest, Context context) {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideUndeleteProductActivity().handleRequest(undeleteProductRequest, context);
    }
}
