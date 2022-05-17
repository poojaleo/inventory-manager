package com.shopify.inventoryservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.shopify.inventoryservice.converters.ShipmentModelConverter;
import com.shopify.inventoryservice.dynamodb.ShipmentDao;
import com.shopify.inventoryservice.dynamodb.table.Shipment;
import com.shopify.inventoryservice.dynamodb.table.ShipmentStatus;
import com.shopify.inventoryservice.exceptions.ShipmentNotFoundException;
import com.shopify.inventoryservice.models.ShipmentModel;
import com.shopify.inventoryservice.models.request.UpdateShipmentRequest;
import com.shopify.inventoryservice.models.response.UpdateShipmentResponse;
import com.shopify.inventoryservice.utils.InventoryManagerServiceUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UpdateShipmentActivityTest {
    private final String companyName = "Dunder Mifflin";
    private final String shippingAddress = "New York, USA";
    private final String createdAt = new Timestamp(System.currentTimeMillis()).toString();
    private final String shipmentId = InventoryManagerServiceUtility.generateId();
    private final String trackingNumber = "GFJKS64380F";


    private final Map<String, Integer> productsShipped = new HashMap<>();

    @Mock
    private ShipmentDao shipmentDao;
    @Mock
    private ShipmentModelConverter shipmentModelConverter;
    @Mock
    private Context context;

    private UpdateShipmentActivity updateShipmentActivity;
    private LambdaLogger lambdaLogger;

    @BeforeEach
    public void setup() {
        initMocks(this);
        updateShipmentActivity = new UpdateShipmentActivity(shipmentDao, shipmentModelConverter);
        productsShipped.put("DM001", 5);
        productsShipped.put("DM002", 10);
        lambdaLogger = new LambdaLogger() {
            @Override
            public void log(String message) {
                System.out.println(message);
            }

            @Override
            public void log(byte[] message) {
                System.out.println(message);
            }
        };
    }

    @Test
    public void  handleRequest_UpdateTrackingNumber_returnsValidUpdateShipmentResponse() {
        UpdateShipmentRequest updateShipmentRequest = UpdateShipmentRequest.builder()
                .withShipmentId(shipmentId)
                .withCompanyName(companyName)
                .withShippingAddress(shippingAddress)
                .withTrackingNumber(trackingNumber)
                .withStatus(ShipmentStatus.LABELCREATED)
                .build();

        Shipment shipmentBeforeUpdate = Shipment.builder()
                .withShipmentId(shipmentId)
                .withShippingAddress(shippingAddress)
                .withCompanyName(companyName)
                .withCreatedAt(createdAt)
                .withProductsShipped(productsShipped)
                .withStatus(ShipmentStatus.LABELCREATED)
                .build();

        Shipment shipmentAfterUpdate = Shipment.builder()
                .withShipmentId(shipmentId)
                .withShippingAddress(shippingAddress)
                .withCompanyName(companyName)
                .withCreatedAt(createdAt)
                .withProductsShipped(productsShipped)
                .withStatus(ShipmentStatus.LABELCREATED)
                .withTrackingNumber(trackingNumber)
                .build();

        ShipmentModel shipmentModel = ShipmentModel.builder()
                .withShipmentId(shipmentId)
                .withShippingAddress(shippingAddress)
                .withCompanyName(companyName)
                .withCreatedAt(createdAt)
                .withProductsShipped(productsShipped)
                .withStatus(ShipmentStatus.LABELCREATED)
                .withTrackingNumber(trackingNumber)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(shipmentDao.getShipment(companyName, shipmentId)).thenReturn(shipmentBeforeUpdate);
        when(shipmentDao.saveShipment(shipmentAfterUpdate)).thenReturn(shipmentAfterUpdate);
        when(shipmentModelConverter.toShipmentModel(shipmentAfterUpdate)).thenReturn(shipmentModel);

        UpdateShipmentResponse response = updateShipmentActivity.handleRequest(updateShipmentRequest, context);

        assertEquals(companyName, response.getShipmentModel().getCompanyName(),
                "Expected company name to be " + companyName);
        assertEquals(shipmentId, response.getShipmentModel().getShipmentId(),
                "Expected shipmentId to be " + shipmentId);
        assertEquals(createdAt, response.getShipmentModel().getCreatedAt(),
                "Expected createdAt time to be " + createdAt);
        assertEquals(shippingAddress, response.getShipmentModel().getShippingAddress(),
                "Expected shippingAddress to be " + shippingAddress);
        assertEquals(ShipmentStatus.LABELCREATED, response.getShipmentModel().getStatus(),
                "Expected status to be " + ShipmentStatus.LABELCREATED.toString());
        assertEquals(trackingNumber, response.getShipmentModel().getTrackingNumber(),
                "Expected tracking number to be " + trackingNumber);
        assertEquals(productsShipped, response.getShipmentModel().getProductsShipped(),
                "Expected products SHipped to be " + productsShipped.toString());
    }

    @Test
    public void  handleRequest_shipmentIdNotValid_throwsShipmentNotFoundException() {
        UpdateShipmentRequest updateShipmentRequest = UpdateShipmentRequest.builder()
                .withShipmentId(shipmentId)
                .withCompanyName(companyName)
                .withShippingAddress(shippingAddress)
                .withTrackingNumber(trackingNumber)
                .withStatus(ShipmentStatus.LABELCREATED)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(shipmentDao.getShipment(companyName, shipmentId)).thenThrow(ShipmentNotFoundException.class);

        assertThrows(ShipmentNotFoundException.class, () -> updateShipmentActivity.handleRequest(updateShipmentRequest, context));


    }


}
