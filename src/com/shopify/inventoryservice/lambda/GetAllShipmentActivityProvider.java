package com.shopify.inventoryservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.shopify.inventoryservice.dependency.DaggerServiceComponent;
import com.shopify.inventoryservice.dependency.ServiceComponent;
import com.shopify.inventoryservice.models.request.GetAllShipmentRequest;
import com.shopify.inventoryservice.models.response.GetAllShipmentResponse;

public class GetAllShipmentActivityProvider implements RequestHandler<GetAllShipmentRequest, GetAllShipmentResponse> {
    public GetAllShipmentActivityProvider() {
    }

    @Override
    public GetAllShipmentResponse handleRequest(GetAllShipmentRequest getAllShipmentRequest, Context context) {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideGetAllShipmentActivity().handleRequest(getAllShipmentRequest, context);
    }
}
