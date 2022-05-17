package com.shopify.inventoryservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.shopify.inventoryservice.converters.ProductModelConverter;
import com.shopify.inventoryservice.dynamodb.ProductDao;
import com.shopify.inventoryservice.dynamodb.table.Product;
import com.shopify.inventoryservice.exceptions.ProductNotFoundException;
import com.shopify.inventoryservice.models.ProductModel;
import com.shopify.inventoryservice.models.request.DeleteProductRequest;
import com.shopify.inventoryservice.models.response.DeleteProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class DeleteProductActivityTest {
    private final String companyName = "Dunder Mifflin";
    private final String sku = "DM001";
    private final String skuNotInDatabase = "DM002";
    private final String name = "Paper";
    private final String description = "Printing paper";
    private final int quantity = 1;
    private final String deleteComments = "Discontinued";
    private final BigDecimal cost = BigDecimal.valueOf(1.11);

    @Mock
    private ProductDao productDao;
    @Mock
    private ProductModelConverter productModelConverter;
    @Mock
    private Context context;

    private DeleteProductActivity deleteProductActivity;
    private LambdaLogger lambdaLogger;

    @BeforeEach
    public void setup() {
        initMocks(this);
        deleteProductActivity = new DeleteProductActivity(productDao, productModelConverter);
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
    public void handleRequest_deleteSku_returnsDeletedProductResponse() {
        DeleteProductRequest deleteProductRequest = DeleteProductRequest.builder()
                .withCompanyName(companyName)
                .withSku(sku)
                .withDeleteComments(deleteComments)
                .build();

        Product product = Product.builder()
                .withCompanyName(companyName)
                .withName(name)
                .withSku(sku)
                .withIsActive(true)
                .withCost(cost)
                .withQuantity(quantity)
                .withDescription(description)
                .build();

        Product deletedProduct = Product.builder()
                .withCompanyName(companyName)
                .withName(name)
                .withSku(sku)
                .withIsActive(false)
                .withCost(cost)
                .withQuantity(quantity)
                .withDescription(description)
                .withDeleteComment(deleteComments)
                .build();

        ProductModel productModel = ProductModel.builder()
                .withCompanyName(companyName)
                .withName(name)
                .withDescription(description)
                .withCost(cost)
                .withQuantity(quantity)
                .withSku(sku)
                .withIsActive(false)
                .withDeleteComment(deleteComments)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(productDao.getActiveProduct(companyName, sku)).thenReturn(product);
        when(productModelConverter.toProductModel(deletedProduct)).thenReturn(productModel);

        DeleteProductResponse response = deleteProductActivity.handleRequest(deleteProductRequest, context);

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
        assertEquals(false, response.getProductModel().isActive(),
                "Expected is Active field to be false");
        assertEquals(deleteComments, response.getProductModel().getDeleteComment(),
                "Expected delete comments to be " + deleteComments);

    }

    @Test
    public void handleRequest_skuNotInDatabase_throwsProductNotFoundException() {
        DeleteProductRequest deleteProductRequest = DeleteProductRequest.builder()
                .withCompanyName(companyName)
                .withSku(skuNotInDatabase)
                .withDeleteComments(deleteComments)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(productDao.getActiveProduct(companyName, skuNotInDatabase)).thenThrow(ProductNotFoundException.class);

        assertThrows(ProductNotFoundException.class, () -> {
            deleteProductActivity.handleRequest(deleteProductRequest, context);
        });
    }
}
