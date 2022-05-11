package com.shopify.inventoryservice.converters;

import com.shopify.inventoryservice.dynamodb.table.Shipment;
import com.shopify.inventoryservice.models.ShipmentModel;

import javax.inject.Inject;

public class ShipmentModelConverter {

    @Inject
    public ShipmentModelConverter() {
    }

    public ShipmentModel toShipmentModel(Shipment shipment) {
        ShipmentModel shipmentModel = ShipmentModel.builder()
                .withCompanyName(shipment.getCompanyName())
                .withShipmentId(shipment.getShipmentId())
                .withCreatedAt(shipment.getCreatedAt())
                .withStatus(shipment.getStatus())
                .withProductsShipped(shipment.getProductsShipped())
                .build();

        if(shipment.getShippingAddress() != null)
            shipmentModel.setShippingAddress(shipment.getShippingAddress());

        if(shipment.getTrackingNumber() != null)
            shipmentModel.setTrackingNumber(shipment.getTrackingNumber());

        return shipmentModel;
    }
}
