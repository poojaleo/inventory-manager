package com.shopify.inventoryservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.shopify.inventoryservice.converters.ShipmentModelConverter;
import com.shopify.inventoryservice.dynamodb.ShipmentDao;
import com.shopify.inventoryservice.dynamodb.table.Shipment;
import com.shopify.inventoryservice.exceptions.ShipmentNotFoundException;
import com.shopify.inventoryservice.models.ShipmentModel;
import com.shopify.inventoryservice.models.request.UpdateShipmentRequest;
import com.shopify.inventoryservice.models.response.UpdateShipmentResponse;

import javax.inject.Inject;

public class UpdateShipmentActivity implements RequestHandler<UpdateShipmentRequest, UpdateShipmentResponse> {
    private final ShipmentDao shipmentDao;
    private ShipmentModelConverter shipmentModelConverter;

    @Inject
    public UpdateShipmentActivity(ShipmentDao shipmentDao, ShipmentModelConverter shipmentModelConverter) {
        this.shipmentDao = shipmentDao;
        this.shipmentModelConverter = shipmentModelConverter;
    }

    @Override
    public UpdateShipmentResponse handleRequest(UpdateShipmentRequest updateShipmentRequest, Context context) {
        LambdaLogger lambdaLogger = context.getLogger();
        lambdaLogger.log(String.format("Received Update Shipment request for company: %s and shipment id: %s ",
                updateShipmentRequest.getCompanyName(), updateShipmentRequest.getShipmentId()));

        Shipment shipment;

        try {
            shipment = shipmentDao.getShipment(updateShipmentRequest.getCompanyName(), updateShipmentRequest.getShipmentId());
        } catch (ShipmentNotFoundException shipmentNotFoundException) {
            throw shipmentNotFoundException;
        }

        shipment.setShippingAddress(updateShipmentRequest.getShippingAddress());
        shipment.setStatus(updateShipmentRequest.getStatus());
        shipment.setTrackingNumber(updateShipmentRequest.getTrackingNumber());

        Shipment shipmentSaved = shipmentDao.saveShipment(shipment);
        ShipmentModel shipmentModel = shipmentModelConverter.toShipmentModel(shipmentSaved);

        return UpdateShipmentResponse.builder()
                .withShipmentModel(shipmentModel)
                .build();
    }
}
