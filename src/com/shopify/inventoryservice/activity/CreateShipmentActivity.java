package com.shopify.inventoryservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.shopify.inventoryservice.converters.ShipmentModelConverter;
import com.shopify.inventoryservice.dynamodb.ProductDao;
import com.shopify.inventoryservice.dynamodb.ShipmentDao;
import com.shopify.inventoryservice.dynamodb.table.Product;
import com.shopify.inventoryservice.dynamodb.table.Shipment;
import com.shopify.inventoryservice.dynamodb.table.ShipmentStatus;
import com.shopify.inventoryservice.exceptions.ProductNotFoundException;
import com.shopify.inventoryservice.models.ShipmentModel;
import com.shopify.inventoryservice.models.request.CreateShipmentRequest;
import com.shopify.inventoryservice.models.response.CreateShipmentResponse;
import com.shopify.inventoryservice.utils.InventoryManagerServiceUtility;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.Map;

public class CreateShipmentActivity implements RequestHandler<CreateShipmentRequest, CreateShipmentResponse> {
    private final ShipmentDao shipmentDao;
    private ShipmentModelConverter shipmentModelConverter;
    private final ProductDao productDao;

    @Inject
    public CreateShipmentActivity(ShipmentDao shipmentDao, ShipmentModelConverter shipmentModelConverter, ProductDao productDao) {
        this.shipmentDao = shipmentDao;
        this.shipmentModelConverter = shipmentModelConverter;
        this.productDao = productDao;
    }

    @Override
    public CreateShipmentResponse handleRequest(CreateShipmentRequest createShipmentRequest, Context context) {
        LambdaLogger lambdaLogger = context.getLogger();
        lambdaLogger.log("Received Create Shipment Request for company:" + createShipmentRequest.getCompanyName());

        String shipmentId = InventoryManagerServiceUtility.generateId();

        Map<String, Integer> productsToBeShipped = createShipmentRequest.getProductsShipped();

        Shipment shipment = Shipment.builder()
                .withCompanyName(createShipmentRequest.getCompanyName())
                .withShipmentId(shipmentId)
                .withCreatedAt(new Timestamp(System.currentTimeMillis()).toString())
                .withStatus(ShipmentStatus.LABELCREATED)
                .withProductsShipped(createShipmentRequest.getProductsShipped())
                .build();

        if(createShipmentRequest.getShippingAddress() != null)
            shipment.setShippingAddress(createShipmentRequest.getShippingAddress());

        try {
            for(Map.Entry<String, Integer> entry : productsToBeShipped.entrySet()) {
                String sku = entry.getKey();
                int quantityToReduce = entry.getValue();
                Product product = productDao.getActiveProduct(createShipmentRequest.getCompanyName(), sku);
                int currentQuantity = product.getQuantity();
                product.setQuantity(currentQuantity - quantityToReduce);
                productDao.saveProduct(product);
            }
        } catch (ProductNotFoundException productNotFoundException) {
              throw productNotFoundException;
        } catch (Exception exception) {
            throw exception;
        }

        Shipment shipmentSaved = shipmentDao.saveShipment(shipment);
        ShipmentModel shipmentModel = shipmentModelConverter.toShipmentModel(shipmentSaved);

        return CreateShipmentResponse.builder()
                .withShipmentModel(shipmentModel)
                .build();
    }
}
