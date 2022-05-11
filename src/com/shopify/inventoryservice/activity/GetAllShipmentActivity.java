package com.shopify.inventoryservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.shopify.inventoryservice.converters.ShipmentModelConverter;
import com.shopify.inventoryservice.dynamodb.ShipmentDao;
import com.shopify.inventoryservice.dynamodb.table.Shipment;
import com.shopify.inventoryservice.models.ShipmentModel;
import com.shopify.inventoryservice.models.request.GetAllShipmentRequest;
import com.shopify.inventoryservice.models.response.GetAllShipmentResponse;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GetAllShipmentActivity implements RequestHandler<GetAllShipmentRequest, GetAllShipmentResponse> {
    private final ShipmentDao shipmentDao;
    private ShipmentModelConverter shipmentModelConverter;

    @Inject
    public GetAllShipmentActivity(ShipmentDao shipmentDao, ShipmentModelConverter shipmentModelConverter) {
        this.shipmentDao = shipmentDao;
        this.shipmentModelConverter = shipmentModelConverter;
    }

    @Override
    public GetAllShipmentResponse handleRequest(GetAllShipmentRequest getAllShipmentRequest, Context context) {
        LambdaLogger lambdaLogger = context.getLogger();
        lambdaLogger.log("Received Get All Shipment Request for company:" + getAllShipmentRequest.getCompanyName());

        List<Shipment> shipmentList = shipmentDao.getAllShipments(getAllShipmentRequest.getCompanyName());

        List<ShipmentModel> shipmentModelList = new ArrayList<>();

        if(shipmentList != null) {
            shipmentModelList = shipmentList.stream()
                    .map(shipment -> shipmentModelConverter.toShipmentModel(shipment))
                    .collect(Collectors.toList());
        }

        return GetAllShipmentResponse.builder()
                .withShipmentList(shipmentModelList)
                .build();
    }
}
