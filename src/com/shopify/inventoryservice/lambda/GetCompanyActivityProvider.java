package com.shopify.inventoryservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.shopify.inventoryservice.dependency.DaggerServiceComponent;
import com.shopify.inventoryservice.dependency.ServiceComponent;
import com.shopify.inventoryservice.models.request.GetCompanyRequest;
import com.shopify.inventoryservice.models.response.GetCompanyResponse;

public class GetCompanyActivityProvider implements RequestHandler<GetCompanyRequest, GetCompanyResponse> {
    public GetCompanyActivityProvider() {
    }

    @Override
    public GetCompanyResponse handleRequest(GetCompanyRequest getCompanyRequest, Context context) {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideGetCompanyActivity().handleRequest(getCompanyRequest, context);
    }
}
