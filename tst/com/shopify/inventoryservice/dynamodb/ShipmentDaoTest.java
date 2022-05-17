package com.shopify.inventoryservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.shopify.inventoryservice.dynamodb.table.Shipment;
import com.shopify.inventoryservice.dynamodb.table.ShipmentStatus;
import com.shopify.inventoryservice.exceptions.ShipmentNotFoundException;
import com.shopify.inventoryservice.utils.InventoryManagerServiceUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.internal.stubbing.defaultanswers.ForwardsInvocations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ShipmentDaoTest {
    private final String companyName = "Dunder Mifflin";
    private final String shippingAddress = "New York, USA";
    private final String createdAt = new Timestamp(System.currentTimeMillis()).toString();
    private final String shipmentIdInDatabase = InventoryManagerServiceUtility.generateId();
    private final String shipmentIdInDatabase2 = InventoryManagerServiceUtility.generateId();
    private final String shipmentIdNotInDatabase = InventoryManagerServiceUtility.generateId();
    private final String trackingNumber = "GFJKS64380F";
    private final Map<String, Integer> productsShipped = new HashMap<>();

    private ShipmentDao shipmentDao;

    @Mock
    private DynamoDBMapper mapper;

    @BeforeEach
    public void setup() {
        initMocks(this);
        shipmentDao = new ShipmentDao(mapper);
        productsShipped.put("DM001", 5);
        productsShipped.put("DM002", 10);
    }

    @Test
    public void getShipment_shipmentIdInDatabase_returnsShipment() {
        Shipment shipment = Shipment.builder()
                .withShipmentId(shipmentIdInDatabase)
                .withShippingAddress(shippingAddress)
                .withCompanyName(companyName)
                .withCreatedAt(createdAt)
                .withProductsShipped(productsShipped)
                .withStatus(ShipmentStatus.LABELCREATED)
                .withTrackingNumber(trackingNumber)
                .build();

        when(mapper.load(Shipment.class, companyName, shipmentIdInDatabase)).thenReturn(shipment);

        Shipment shipmentReturned = shipmentDao.getShipment(companyName, shipmentIdInDatabase);

        assertEquals(companyName, shipmentReturned.getCompanyName(),
                "Expected company name to be " + companyName);
        assertEquals(shipmentIdInDatabase, shipmentReturned.getShipmentId(),
                "Expected shipmentId to be " + shipmentIdInDatabase);
        assertEquals(createdAt, shipmentReturned.getCreatedAt(),
                "Expected createdAt time to be " + createdAt);
        assertEquals(shippingAddress, shipmentReturned.getShippingAddress(),
                "Expected shippingAddress to be " + shippingAddress);
        assertEquals(ShipmentStatus.LABELCREATED, shipmentReturned.getStatus(),
                "Expected status to be " + ShipmentStatus.LABELCREATED.toString());
        assertEquals(trackingNumber, shipmentReturned.getTrackingNumber(),
                "Expected tracking number to be " + trackingNumber);
        assertEquals(productsShipped, shipmentReturned.getProductsShipped(),
                "Expected products SHipped to be " + productsShipped.toString());

    }

    @Test
    public void getShipment_shipmentIdNotInDatabase_throwsShipmentNotFoundException() {
        when(mapper.load(Shipment.class, companyName, shipmentIdNotInDatabase)).thenThrow(ShipmentNotFoundException.class);

        assertThrows(ShipmentNotFoundException.class, () -> shipmentDao.getShipment(companyName, shipmentIdNotInDatabase));
    }

    @Test
    public void getAllShipments_companyHasTwoShipments_returnsTwoShipments() {
        Shipment firstShipment = Shipment.builder()
                .withShipmentId(shipmentIdInDatabase)
                .withShippingAddress(shippingAddress)
                .withCompanyName(companyName)
                .withCreatedAt(createdAt)
                .withProductsShipped(productsShipped)
                .withStatus(ShipmentStatus.LABELCREATED)
                .withTrackingNumber(trackingNumber)
                .build();

        Shipment secondShipment = Shipment.builder()
                .withShipmentId(shipmentIdInDatabase2)
                .withShippingAddress(shippingAddress)
                .withCompanyName(companyName)
                .withCreatedAt(createdAt)
                .withProductsShipped(productsShipped)
                .withStatus(ShipmentStatus.LABELCREATED)
                .withTrackingNumber(trackingNumber)
                .build();

        List<Shipment> shipmentList = new ArrayList<>();
        shipmentList.add(firstShipment);
        shipmentList.add(secondShipment);

        when(mapper.query(eq(Shipment.class), any(DynamoDBQueryExpression.class)))
                .thenReturn(mock(PaginatedQueryList.class, withSettings().defaultAnswer(new ForwardsInvocations(shipmentList))));

        List<Shipment> returnedShipmentList = shipmentDao.getAllShipments(companyName);
        assertEquals(2, returnedShipmentList.size(), "Expected size to be 2");
    }

    @Test
    public void getAllShipments_companyHasNoShipments_returnsEmptyList() {

        List<Shipment> shipmentList = new ArrayList<>();


        when(mapper.query(eq(Shipment.class), any(DynamoDBQueryExpression.class)))
                .thenReturn(mock(PaginatedQueryList.class, withSettings().defaultAnswer(new ForwardsInvocations(shipmentList))));

        List<Shipment> returnedShipmentList = shipmentDao.getAllShipments(companyName);
        assertEquals(0, returnedShipmentList.size(), "Expected size to be 0");
    }

    @Test
    public void saveShipment_validShipment_returnsShipment() {
        Shipment shipment = Shipment.builder()
                .withShipmentId(shipmentIdInDatabase)
                .withShippingAddress(shippingAddress)
                .withCompanyName(companyName)
                .withCreatedAt(createdAt)
                .withProductsShipped(productsShipped)
                .withStatus(ShipmentStatus.LABELCREATED)
                .withTrackingNumber(trackingNumber)
                .build();

        Shipment shipmentReturned = shipmentDao.saveShipment(shipment);

        assertEquals(companyName, shipmentReturned.getCompanyName(),
                "Expected company name to be " + companyName);
        assertEquals(shipmentIdInDatabase, shipmentReturned.getShipmentId(),
                "Expected shipmentId to be " + shipmentIdInDatabase);
        assertEquals(createdAt, shipmentReturned.getCreatedAt(),
                "Expected createdAt time to be " + createdAt);
        assertEquals(shippingAddress, shipmentReturned.getShippingAddress(),
                "Expected shippingAddress to be " + shippingAddress);
        assertEquals(ShipmentStatus.LABELCREATED, shipmentReturned.getStatus(),
                "Expected status to be " + ShipmentStatus.LABELCREATED.toString());
        assertEquals(trackingNumber, shipmentReturned.getTrackingNumber(),
                "Expected tracking number to be " + trackingNumber);
        assertEquals(productsShipped, shipmentReturned.getProductsShipped(),
                "Expected products SHipped to be " + productsShipped.toString());
    }

}
