package com.shopify.inventoryservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.shopify.inventoryservice.dependency.DaggerServiceComponent;
import com.shopify.inventoryservice.dependency.ServiceComponent;
import com.shopify.inventoryservice.models.request.GetAllInactiveProductRequest;
import com.shopify.inventoryservice.models.response.GetAllInactiveProductResponse;

import javax.swing.plaf.synth.Region;

public class GetAllInactiveProductsActivityProvider implements RequestHandler<GetAllInactiveProductRequest, GetAllInactiveProductResponse> {
    public GetAllInactiveProductsActivityProvider() {
    }

    @Override
    public GetAllInactiveProductResponse handleRequest(GetAllInactiveProductRequest getAllInactiveProductRequest, Context context) {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideGetAllInactiveProductActivity().handleRequest(getAllInactiveProductRequest,context);
    }
}
