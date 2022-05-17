package com.shopify.inventoryservice.converters;

import com.shopify.inventoryservice.dynamodb.table.Shipment;
import com.shopify.inventoryservice.dynamodb.table.ShipmentStatus;
import com.shopify.inventoryservice.models.ShipmentModel;
import com.shopify.inventoryservice.utils.InventoryManagerServiceUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShippingModelConverterTest {
    private final String companyName = "Dunder Mifflin";
    private final String shippingAddress = "New York, USA";
    private final String createdAt = new Timestamp(System.currentTimeMillis()).toString();
    private final String shipmentId = InventoryManagerServiceUtility.generateId();
    private final String trackingNumber = "GFJKS64380F";
    private final Map<String, Integer> productsShipped = new HashMap<>();

    private ShipmentModelConverter shipmentModelConverter;

    @BeforeEach
    public void setup() {
        shipmentModelConverter = new ShipmentModelConverter();
        productsShipped.put("DM001", 5);
        productsShipped.put("DM002", 10);
    }


    @Test
    public void toShipmentModel_withShipment_returnsShipmentModel() {
        Shipment shipment = Shipment.builder()
                .withShipmentId(shipmentId)
                .withShippingAddress(shippingAddress)
                .withCompanyName(companyName)
                .withCreatedAt(createdAt)
                .withProductsShipped(productsShipped)
                .withStatus(ShipmentStatus.LABELCREATED)
                .withTrackingNumber(trackingNumber)
                .build();

        ShipmentModel shipmentModel = shipmentModelConverter.toShipmentModel(shipment);

        assertEquals(companyName, shipmentModel.getCompanyName(),
                "Expected company name to be " + companyName);
        assertEquals(shipmentId, shipmentModel.getShipmentId(),
                "Expected shipmentId to be " + shipmentId);
        assertEquals(createdAt, shipmentModel.getCreatedAt(),
                "Expected createdAt time to be " + createdAt);
        assertEquals(shippingAddress, shipmentModel.getShippingAddress(),
                "Expected shippingAddress to be " + shippingAddress);
        assertEquals(ShipmentStatus.LABELCREATED, shipmentModel.getStatus(),
                "Expected status to be " + ShipmentStatus.LABELCREATED.toString());
        assertEquals(trackingNumber, shipmentModel.getTrackingNumber(),
                "Expected tracking number to be " + trackingNumber);
        assertEquals(productsShipped, shipmentModel.getProductsShipped(),
                "Expected products SHipped to be " + productsShipped.toString());
    }
}
