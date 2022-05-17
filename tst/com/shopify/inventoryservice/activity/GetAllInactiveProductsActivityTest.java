package com.shopify.inventoryservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.shopify.inventoryservice.converters.ProductModelConverter;
import com.shopify.inventoryservice.dynamodb.ProductDao;
import com.shopify.inventoryservice.dynamodb.table.Product;
import com.shopify.inventoryservice.models.ProductModel;
import com.shopify.inventoryservice.models.request.GetAllInactiveProductRequest;
import com.shopify.inventoryservice.models.response.GetAllInactiveProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetAllInactiveProductsActivityTest {
    private final String companyName = "Dunder Mifflin";
    private final String skuOne = "DM001";
    private final String skuTwo = "DM002";
    private final String skuDoesNotExist = "DM005";
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

    private GetAllInactiveProductsActivity getAllInactiveProductsActivity;
    private LambdaLogger lambdaLogger;

    @BeforeEach
    public void setup() {
        initMocks(this);
        getAllInactiveProductsActivity = new GetAllInactiveProductsActivity(productDao, productModelConverter);
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
    public void handleRequest_companyHasTwoInactiveProducts_returnsTwoInactiveProducts() {
        Product firstProduct = Product.builder()
                .withCompanyName(companyName)
                .withSku(skuOne)
                .withName(name)
                .withDescription(description)
                .withQuantity(quantity)
                .withCost(cost)
                .withIsActive(true)
                .build();

        ProductModel firstProductModel = ProductModel.builder()
                .withCompanyName(companyName)
                .withSku(skuOne)
                .withName(name)
                .withDescription(description)
                .withQuantity(quantity)
                .withCost(cost)
                .withIsActive(true)
                .build();

        Product secondProduct = Product.builder()
                .withCompanyName(companyName)
                .withSku(skuTwo)
                .withName(name)
                .withDescription(description)
                .withQuantity(quantity)
                .withCost(cost)
                .withIsActive(true)
                .build();

        ProductModel secondProductModel = ProductModel.builder()
                .withCompanyName(companyName)
                .withSku(skuTwo)
                .withName(name)
                .withDescription(description)
                .withQuantity(quantity)
                .withCost(cost)
                .withIsActive(true)
                .build();

        List<Product> productList = new ArrayList<>();
        productList.add(firstProduct);
        productList.add(secondProduct);


        GetAllInactiveProductRequest request = GetAllInactiveProductRequest.builder()
                .withCompanyName(companyName)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(productDao.getAllInactiveProducts(companyName)).thenReturn(productList);
        when(productModelConverter.toProductModel(firstProduct)).thenReturn(firstProductModel);
        when(productModelConverter.toProductModel(secondProduct)).thenReturn(secondProductModel);

        GetAllInactiveProductResponse response = getAllInactiveProductsActivity.handleRequest(request, context);

        verify(productDao).getAllInactiveProducts(companyName);
        assertEquals(2, response.getInactiveProductsList().size(), "Expected two products to be returned");
    }

    @Test
    public void handleRequest_companyHasZeroInactiveProducts_returnsEmptyList() {
        GetAllInactiveProductRequest request = GetAllInactiveProductRequest.builder()
                .withCompanyName(companyName)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(productDao.getAllInactiveProducts(companyName)).thenReturn(new ArrayList<>());

        GetAllInactiveProductResponse response = getAllInactiveProductsActivity.handleRequest(request, context);
        assertEquals(0, response.getInactiveProductsList().size(), "Expected no products to be returned");
    }
}
