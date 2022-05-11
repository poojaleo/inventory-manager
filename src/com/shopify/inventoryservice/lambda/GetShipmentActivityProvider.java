package com.shopify.inventoryservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.shopify.inventoryservice.dependency.DaggerServiceComponent;
import com.shopify.inventoryservice.dependency.ServiceComponent;
import com.shopify.inventoryservice.models.request.GetShipmentRequest;
import com.shopify.inventoryservice.models.response.GetShipmentResponse;

public class GetShipmentActivityProvider implements RequestHandler<GetShipmentRequest, GetShipmentResponse> {
    public GetShipmentActivityProvider() {
    }

    @Override
    public GetShipmentResponse handleRequest(GetShipmentRequest getShipmentRequest, Context context) {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideGetShipmentActivity().handleRequest(getShipmentRequest, context);
    }
}
