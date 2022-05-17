package com.shopify.inventoryservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.shopify.inventoryservice.converters.ShipmentModelConverter;
import com.shopify.inventoryservice.dynamodb.ShipmentDao;
import com.shopify.inventoryservice.dynamodb.table.Shipment;
import com.shopify.inventoryservice.dynamodb.table.ShipmentStatus;
import com.shopify.inventoryservice.models.ShipmentModel;
import com.shopify.inventoryservice.models.request.GetAllShipmentRequest;
import com.shopify.inventoryservice.models.response.GetAllShipmentResponse;
import com.shopify.inventoryservice.utils.InventoryManagerServiceUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetAllShipmentActivityTest {
    private final String companyName = "Dunder Mifflin";
    private final String shippingAddress = "New York, USA";
    private final String createdAt = new Timestamp(System.currentTimeMillis()).toString();
    private final String firstShipmentId = InventoryManagerServiceUtility.generateId();
    private final String secondShipmentId = InventoryManagerServiceUtility.generateId();
    private final String trackingNumber = "GFJKS64380F";
    private final Map<String, Integer> productsShipped = new HashMap<>();

    @Mock
    private ShipmentDao shipmentDao;
    @Mock
    private ShipmentModelConverter shipmentModelConverter;
    @Mock
    private Context context;

    private GetAllShipmentActivity getAllShipmentActivity;
    private LambdaLogger lambdaLogger;

    @BeforeEach
    public void setup() {
        initMocks(this);
        getAllShipmentActivity = new GetAllShipmentActivity(shipmentDao, shipmentModelConverter);
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
    public void handleRequest_companyHasTwoShipments_returnsTwoShipments() {
        GetAllShipmentRequest getAllShipmentRequest = GetAllShipmentRequest.builder()
                .withCompanyName(companyName)
                .build();

        Shipment shipment1 = Shipment.builder()
                .withShipmentId(firstShipmentId)
                .withShippingAddress(shippingAddress)
                .withCompanyName(companyName)
                .withCreatedAt(createdAt)
                .withProductsShipped(productsShipped)
                .withStatus(ShipmentStatus.LABELCREATED)
                .withTrackingNumber(trackingNumber)
                .build();

        Shipment shipment2 = Shipment.builder()
                .withShipmentId(secondShipmentId)
                .withShippingAddress(shippingAddress)
                .withCompanyName(companyName)
                .withCreatedAt(createdAt)
                .withProductsShipped(productsShipped)
                .withStatus(ShipmentStatus.COMPLETED)
                .withTrackingNumber(trackingNumber)
                .build();

        ShipmentModel shipmentModel1 = ShipmentModel.builder()
                .withShipmentId(firstShipmentId)
                .withShippingAddress(shippingAddress)
                .withCompanyName(companyName)
                .withCreatedAt(createdAt)
                .withProductsShipped(productsShipped)
                .withStatus(ShipmentStatus.LABELCREATED)
                .withTrackingNumber(trackingNumber)
                .build();

        ShipmentModel shipmentModel2 = ShipmentModel.builder()
                .withShipmentId(secondShipmentId)
                .withShippingAddress(shippingAddress)
                .withCompanyName(companyName)
                .withCreatedAt(createdAt)
                .withProductsShipped(productsShipped)
                .withStatus(ShipmentStatus.COMPLETED)
                .withTrackingNumber(trackingNumber)
                .build();

        List<Shipment> shipmentList = new ArrayList<>();
        shipmentList.add(shipment1);
        shipmentList.add(shipment2);

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(shipmentDao.getAllShipments(companyName)).thenReturn(shipmentList);
        when(shipmentModelConverter.toShipmentModel(shipment1)).thenReturn(shipmentModel1);
        when(shipmentModelConverter.toShipmentModel(shipment2)).thenReturn(shipmentModel2);

        GetAllShipmentResponse response = getAllShipmentActivity.handleRequest(getAllShipmentRequest, context);

        verify(shipmentDao).getAllShipments(companyName);
        assertEquals(2, response.getShipmentModelList().size(), "Expected two shipments to be returned");
    }

    @Test
    public void handleRequest_companyHasZeroShipments_returnsZeroShipments() {
        GetAllShipmentRequest getAllShipmentRequest = GetAllShipmentRequest.builder()
                .withCompanyName(companyName)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(shipmentDao.getAllShipments(companyName)).thenReturn(new ArrayList<>());

        GetAllShipmentResponse response = getAllShipmentActivity.handleRequest(getAllShipmentRequest, context);

        verify(shipmentDao).getAllShipments(companyName);
        assertEquals(0, response.getShipmentModelList().size(), "Expected zero shipments to be returned");

    }
}
