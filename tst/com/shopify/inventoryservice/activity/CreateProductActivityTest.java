package com.shopify.inventoryservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.shopify.inventoryservice.converters.ProductModelConverter;
import com.shopify.inventoryservice.dynamodb.ProductDao;
import com.shopify.inventoryservice.dynamodb.table.Product;
import com.shopify.inventoryservice.exceptions.InvalidAttributeValueException;
import com.shopify.inventoryservice.exceptions.ProductAlreadyExistsException;
import com.shopify.inventoryservice.exceptions.ProductNotFoundException;
import com.shopify.inventoryservice.models.ProductModel;
import com.shopify.inventoryservice.models.request.CreateProductRequest;
import com.shopify.inventoryservice.models.response.CreateProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CreateProductActivityTest {
    private final String companyName = "Dunder Mifflin";
    private final String sku = "DM001";
    private final String skuAlreadyExist = "DM002";
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

    private CreateProductActivity createProductActivity;
    private LambdaLogger lambdaLogger;

    @BeforeEach
    public void setup() {
        initMocks(this);
        createProductActivity = new CreateProductActivity(productDao, productModelConverter);
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
    public void handleRequest_createValidProduct_returnsValidCreateProductResponse() {
        CreateProductRequest createProductRequest = CreateProductRequest.builder()
                .withCompanyName(companyName)
                .withName(name)
                .withDescription(description)
                .withCost(cost)
                .withQuantity(quantity)
                .withSku(sku)
                .build();

        ProductModel productModel = ProductModel.builder()
                .withCompanyName(companyName)
                .withName(name)
                .withDescription(description)
                .withCost(cost)
                .withQuantity(quantity)
                .withSku(sku)
                .withIsActive(true)
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

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(productDao.getProductActiveAndInactive(companyName, sku)).thenThrow(ProductNotFoundException.class);
        when(productDao.saveProduct(product)).then(AdditionalAnswers.returnsFirstArg());
        when(productModelConverter.toProductModel(product)).thenReturn(productModel);

        CreateProductResponse createProductResponse = createProductActivity.handleRequest(createProductRequest, context);
        assertEquals(companyName, createProductResponse.getProductModel().getCompanyName(),
                "Expected company name to be " + companyName);
        assertEquals(sku, createProductResponse.getProductModel().getSku(),
                "Expected sku to be " + sku);
        assertEquals(name, createProductResponse.getProductModel().getName(),
                "Expected name to be " + name);
        assertEquals(cost, createProductResponse.getProductModel().getCost(), "Expected cost to be " + cost);
        assertEquals(quantity, createProductResponse.getProductModel().getQuantity(),
                "Expected quantity to be " + quantity);
        assertEquals(description, createProductResponse.getProductModel().getDescription(),
                "Expected description to be " + description);
        assertEquals(true, createProductResponse.getProductModel().isActive(),
                "Expected is Active field to be true");

    }

    @Test
    public void handleRequest_withQuantityLessThanZero_throwsInvalidAttributeValueException() {
        CreateProductRequest createProductRequest = CreateProductRequest.builder()
                .withCompanyName(companyName)
                .withName(name)
                .withDescription(description)
                .withCost(cost)
                .withQuantity(-1)
                .withSku(sku)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);

        assertThrows(InvalidAttributeValueException.class, () ->
                createProductActivity.handleRequest(createProductRequest, context));
    }

    @Test
    public void handleRequest_withCostLessThanZero_throwsInvalidAttributeValueException() {
        CreateProductRequest createProductRequest = CreateProductRequest.builder()
                .withCompanyName(companyName)
                .withName(name)
                .withDescription(description)
                .withCost(BigDecimal.valueOf(-1))
                .withQuantity(quantity)
                .withSku(sku)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);

        assertThrows(InvalidAttributeValueException.class, () ->
                createProductActivity.handleRequest(createProductRequest, context));
    }

    @Test
    public void handleRequest_skuAlreadyExist_throwsProductAlreadyExistsException() {
        CreateProductRequest createProductRequest = CreateProductRequest.builder()
                .withCompanyName(companyName)
                .withName(name)
                .withDescription(description)
                .withCost(cost)
                .withQuantity(quantity)
                .withSku(skuAlreadyExist)
                .build();

        Product product = Product.builder()
                .withCompanyName(companyName)
                .withName(name)
                .withSku(skuAlreadyExist)
                .withIsActive(true)
                .withCost(cost)
                .withQuantity(quantity)
                .withDescription(description)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(productDao.getProductActiveAndInactive(companyName, skuAlreadyExist)).thenReturn(product);

        assertThrows(ProductAlreadyExistsException.class, () ->
                createProductActivity.handleRequest(createProductRequest, context));

    }

}
