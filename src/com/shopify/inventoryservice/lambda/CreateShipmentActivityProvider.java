package com.shopify.inventoryservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.shopify.inventoryservice.dependency.DaggerServiceComponent;
import com.shopify.inventoryservice.dependency.ServiceComponent;
import com.shopify.inventoryservice.models.request.CreateShipmentRequest;
import com.shopify.inventoryservice.models.response.CreateShipmentResponse;

public class CreateShipmentActivityProvider implements RequestHandler<CreateShipmentRequest, CreateShipmentResponse> {
    public CreateShipmentActivityProvider() {
    }

    @Override
    public CreateShipmentResponse handleRequest(CreateShipmentRequest createShipmentRequest, Context context) {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideCreateShipmentActivity().handleRequest(createShipmentRequest, context);
    }
}
