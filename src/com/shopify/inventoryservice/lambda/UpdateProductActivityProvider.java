package com.shopify.inventoryservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.shopify.inventoryservice.dependency.DaggerServiceComponent;
import com.shopify.inventoryservice.dependency.ServiceComponent;
import com.shopify.inventoryservice.models.request.UpdateProductRequest;
import com.shopify.inventoryservice.models.response.UpdateProductResponse;

public class UpdateProductActivityProvider implements RequestHandler<UpdateProductRequest, UpdateProductResponse> {
    public UpdateProductActivityProvider() {
    }

    @Override
    public UpdateProductResponse handleRequest(UpdateProductRequest updateProductRequest, Context context) {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideUpdateProductActivity().handleRequest(updateProductRequest, context);
    }
}
