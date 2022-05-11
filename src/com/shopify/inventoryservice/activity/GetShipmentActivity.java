package com.shopify.inventoryservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.shopify.inventoryservice.converters.ShipmentModelConverter;
import com.shopify.inventoryservice.dynamodb.ShipmentDao;
import com.shopify.inventoryservice.dynamodb.table.Shipment;
import com.shopify.inventoryservice.exceptions.ShipmentNotFoundException;
import com.shopify.inventoryservice.models.ShipmentModel;
import com.shopify.inventoryservice.models.request.GetShipmentRequest;
import com.shopify.inventoryservice.models.response.GetShipmentResponse;

import javax.inject.Inject;

public class GetShipmentActivity implements RequestHandler<GetShipmentRequest, GetShipmentResponse> {
    private final ShipmentDao shipmentDao;
    private ShipmentModelConverter shipmentModelConverter;

    @Inject
    public GetShipmentActivity(ShipmentDao shipmentDao, ShipmentModelConverter shipmentModelConverter) {
        this.shipmentDao = shipmentDao;
        this.shipmentModelConverter = shipmentModelConverter;
    }

    @Override
    public GetShipmentResponse handleRequest(GetShipmentRequest getShipmentRequest, Context context) {
        LambdaLogger lambdaLogger = context.getLogger();
        lambdaLogger.log("Received Get Shipment Request for company:" + getShipmentRequest.getCompanyName() +
                " and shipment id: " + getShipmentRequest.getShipmentId());

        Shipment shipment;

        try {
           shipment = shipmentDao.getShipment(getShipmentRequest.getCompanyName(), getShipmentRequest.getShipmentId());
        } catch (ShipmentNotFoundException shipmentNotFoundException) {
            throw shipmentNotFoundException;
        }

        ShipmentModel shipmentModel = shipmentModelConverter.toShipmentModel(shipment);

        return GetShipmentResponse.builder()
                .withShipmentModel(shipmentModel)
                .build();

    }
}
