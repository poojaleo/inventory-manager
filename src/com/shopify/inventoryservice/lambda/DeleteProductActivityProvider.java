package com.shopify.inventoryservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.shopify.inventoryservice.dependency.DaggerServiceComponent;
import com.shopify.inventoryservice.dependency.ServiceComponent;
import com.shopify.inventoryservice.models.request.DeleteProductRequest;
import com.shopify.inventoryservice.models.response.DeleteProductResponse;

public class DeleteProductActivityProvider implements RequestHandler<DeleteProductRequest, DeleteProductResponse> {
    public DeleteProductActivityProvider() {
    }

    @Override
    public DeleteProductResponse handleRequest(DeleteProductRequest deleteProductRequest, Context context) {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideDeleteProductActivity().handleRequest(deleteProductRequest, context);
    }
}
