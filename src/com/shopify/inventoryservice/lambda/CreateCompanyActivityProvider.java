package com.shopify.inventoryservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.shopify.inventoryservice.dependency.DaggerServiceComponent;
import com.shopify.inventoryservice.dependency.ServiceComponent;
import com.shopify.inventoryservice.models.request.CreateCompanyRequest;
import com.shopify.inventoryservice.models.response.CreateCompanyResponse;

public class CreateCompanyActivityProvider implements RequestHandler<CreateCompanyRequest, CreateCompanyResponse> {

    public CreateCompanyActivityProvider() {
    }

    @Override
    public CreateCompanyResponse handleRequest(CreateCompanyRequest createCompanyRequest, Context context) {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideCreateCompanyActivity().handleRequest(createCompanyRequest, context);
    }
}
