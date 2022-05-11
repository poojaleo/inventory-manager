package com.shopify.inventoryservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.shopify.inventoryservice.dependency.DaggerServiceComponent;
import com.shopify.inventoryservice.dependency.ServiceComponent;
import com.shopify.inventoryservice.models.request.GetAllActiveProductRequest;
import com.shopify.inventoryservice.models.response.GetAllActiveProductResponse;

public class GetAllActiveProductsActivityProvider implements RequestHandler<GetAllActiveProductRequest, GetAllActiveProductResponse> {
    public GetAllActiveProductsActivityProvider() {
    }

    @Override
    public GetAllActiveProductResponse handleRequest(GetAllActiveProductRequest getAllActiveProductRequest, Context context) {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideGetAllActiveProductActivity().handleRequest(getAllActiveProductRequest, context);
    }
}
