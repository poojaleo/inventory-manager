package com.shopify.inventoryservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.shopify.inventoryservice.converters.ShipmentModelConverter;
import com.shopify.inventoryservice.dynamodb.ProductDao;
import com.shopify.inventoryservice.dynamodb.ShipmentDao;
import com.shopify.inventoryservice.dynamodb.table.Product;
import com.shopify.inventoryservice.dynamodb.table.Shipment;
import com.shopify.inventoryservice.dynamodb.table.ShipmentStatus;
import com.shopify.inventoryservice.models.ShipmentModel;
import com.shopify.inventoryservice.models.request.CreateShipmentRequest;
import com.shopify.inventoryservice.models.response.CreateShipmentResponse;
import com.shopify.inventoryservice.utils.InventoryManagerServiceUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CreateShipmentActivityTest {
    private final String companyName = "Dunder Mifflin";
    private final String shippingAddress = "New York, USA";
    private final String createdAt = new Timestamp(System.currentTimeMillis()).toString();
    private final String shipmentId = InventoryManagerServiceUtility.generateId();
    private final String name = "Paper";
    private final String description = "Printing paper";
    private final int quantity = 100;
    private final BigDecimal cost = BigDecimal.valueOf(1.11);
    private final Map<String, Integer> productsShipped = new HashMap<>();

    @Mock
    private ProductDao productDao;
    @Mock
    private ShipmentDao shipmentDao;
    @Mock
    private ShipmentModelConverter shipmentModelConverter;
    @Mock
    private Context context;

    private CreateShipmentActivity createShipmentActivity;
    private LambdaLogger lambdaLogger;

    @BeforeEach
    public void setup() {
        initMocks(this);
        createShipmentActivity = new CreateShipmentActivity(shipmentDao, shipmentModelConverter, productDao);
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
    public void handleRequest_createValidShipment_returnsValidCreateShipmentResponse() {
        CreateShipmentRequest createShipmentRequest = CreateShipmentRequest.builder()
                .withCompanyName(companyName)
                .withShippingAddress(shippingAddress)
                .withProductsShipped(productsShipped)
                .build();

        Product product1 = Product.builder()
                .withCompanyName(companyName)
                .withName(name)
                .withSku("DM001")
                .withIsActive(true)
                .withCost(cost)
                .withQuantity(quantity)
                .withDescription(description)
                .build();

        Product product2 = Product.builder()
                .withCompanyName(companyName)
                .withName(name)
                .withSku("DM002")
                .withIsActive(true)
                .withCost(cost)
                .withQuantity(quantity)
                .withDescription(description)
                .build();

        ShipmentModel shipmentModel = ShipmentModel.builder()
                .withShipmentId(shipmentId)
                .withShippingAddress(shippingAddress)
                .withCompanyName(companyName)
                .withCreatedAt(createdAt)
                .withProductsShipped(productsShipped)
                .withStatus(ShipmentStatus.LABELCREATED)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(productDao.getActiveProduct(companyName, "DM001")).thenReturn(product1);
        when(productDao.getActiveProduct(companyName, "DM002")).thenReturn(product2);
        when(shipmentDao.saveShipment(any(Shipment.class))).then(AdditionalAnswers.returnsFirstArg());
        when(shipmentModelConverter.toShipmentModel(any(Shipment.class))).thenReturn(shipmentModel);

        CreateShipmentResponse response = createShipmentActivity.handleRequest(createShipmentRequest, context);

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
        assertEquals(null, response.getShipmentModel().getTrackingNumber(),
                "Expected tracking number to be null");
        assertEquals(productsShipped, response.getShipmentModel().getProductsShipped(),
                "Expected products SHipped to be " + productsShipped.toString());

    }
}
