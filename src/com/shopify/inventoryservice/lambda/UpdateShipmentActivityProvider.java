package com.shopify.inventoryservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.shopify.inventoryservice.dependency.DaggerServiceComponent;
import com.shopify.inventoryservice.dependency.ServiceComponent;
import com.shopify.inventoryservice.models.request.UpdateShipmentRequest;
import com.shopify.inventoryservice.models.response.UpdateShipmentResponse;

public class UpdateShipmentActivityProvider implements RequestHandler<UpdateShipmentRequest, UpdateShipmentResponse> {
    public UpdateShipmentActivityProvider() {
    }

    @Override
    public UpdateShipmentResponse handleRequest(UpdateShipmentRequest updateShipmentRequest, Context context) {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideUpdateShipmentActivity().handleRequest(updateShipmentRequest, context);
    }
}
