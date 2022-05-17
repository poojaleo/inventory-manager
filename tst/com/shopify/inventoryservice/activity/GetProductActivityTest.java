package com.shopify.inventoryservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.shopify.inventoryservice.converters.ProductModelConverter;
import com.shopify.inventoryservice.dynamodb.ProductDao;
import com.shopify.inventoryservice.dynamodb.table.Product;
import com.shopify.inventoryservice.exceptions.ProductNotFoundException;
import com.shopify.inventoryservice.models.ProductModel;
import com.shopify.inventoryservice.models.request.GetProductRequest;
import com.shopify.inventoryservice.models.response.GetProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetProductActivityTest {
    private final String companyName = "Dunder Mifflin";
    private final String sku = "DM001";
    private final String skuDoesNotExist = "DM002";
    private final String name = "Paper";
    private final String description = "Printing paper";
    private final int quantity = 1;
    private final BigDecimal cost = BigDecimal.valueOf(1.11);

    @Mock
    private ProductDao productDao;
    @Mock
    private ProductModelConverter productModelConverter;
    @Mock
    private Context context;

    private GetProductActivity getProductActivity;
    private LambdaLogger lambdaLogger;

    @BeforeEach
    public void setup() {
        initMocks(this);
        getProductActivity = new GetProductActivity(productDao, productModelConverter);
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
    public void handleRequest_activeProductInDatabase_returnsProductModel() {
        Product product = Product.builder()
                .withCompanyName(companyName)
                .withSku(sku)
                .withName(name)
                .withDescription(description)
                .withQuantity(quantity)
                .withCost(cost)
                .withIsActive(true)
                .build();

        ProductModel productModel = ProductModel.builder()
                .withCompanyName(companyName)
                .withSku(sku)
                .withName(name)
                .withDescription(description)
                .withQuantity(quantity)
                .withCost(cost)
                .withIsActive(true)
                .build();

        GetProductRequest getProductRequest = GetProductRequest.builder()
                .withCompanyName(companyName)
                .withSku(sku)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(productDao.getActiveProduct(companyName, sku)).thenReturn(product);
        when(productModelConverter.toProductModel(product)).thenReturn(productModel);

        GetProductResponse response = getProductActivity.handleRequest(getProductRequest, context);
        assertEquals(companyName, response.getProductModel().getCompanyName(),
                "Expected company name to be " + companyName);
        assertEquals(sku, response.getProductModel().getSku(),
                "Expected sku to be " + sku);
        assertEquals(name, response.getProductModel().getName(),
                "Expected name to be " + name);
        assertEquals(cost, response.getProductModel().getCost(), "Expected cost to be " + cost);
        assertEquals(quantity, response.getProductModel().getQuantity(),
                "Expected quantity to be " + quantity);
        assertEquals(description, response.getProductModel().getDescription(),
                "Expected description to be " + description);
        assertEquals(true, response.getProductModel().isActive(),
                "Expected is Active field to be true");
    }

    @Test
    public void handleRequest_activeProductNotInDatabase_throwsProductNotFoundException() {

        GetProductRequest getProductRequest = GetProductRequest.builder()
                .withCompanyName(companyName)
                .withSku(skuDoesNotExist)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(productDao.getActiveProduct(companyName, skuDoesNotExist)).thenThrow(ProductNotFoundException.class);


        assertThrows(ProductNotFoundException.class, () -> getProductActivity.handleRequest(getProductRequest, context));
    }

}
