package com.shopify.inventoryservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.shopify.inventoryservice.dynamodb.table.Shipment;
import com.shopify.inventoryservice.exceptions.ShipmentNotFoundException;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class ShipmentDao {
    private final DynamoDBMapper mapper;

    @Inject
    public ShipmentDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public Shipment getShipment(String companyName, String shipmentId) {
        Shipment shipment = mapper.load(Shipment.class, companyName, shipmentId);

        if(shipment == null) {
            throw new ShipmentNotFoundException("Shipment not found for id: " + shipmentId);
        }

        return shipment;
    }

    public List<Shipment> getAllShipments(String companyName) {
        Shipment shipment = new Shipment();
        shipment.setCompanyName(companyName);

        DynamoDBQueryExpression<Shipment> queryExpression = new DynamoDBQueryExpression<Shipment>()
                .withHashKeyValues(shipment);

        return new ArrayList<>(mapper.query(Shipment.class, queryExpression));
    }

    public Shipment saveShipment(Shipment shipment) {
        mapper.save(shipment);
        return shipment;
    }

}
