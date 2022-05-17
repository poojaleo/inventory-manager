package com.shopify.inventoryservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.shopify.inventoryservice.dynamodb.table.Product;
import com.shopify.inventoryservice.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.internal.stubbing.defaultanswers.ForwardsInvocations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ProductDaoTest {
    private final String companyName = "Dunder Mifflin";
    private final String activeSkuOne = "DM001";
    private final String activeSkuTwo = "DM004";
    private final String inactiveSkuOne = "DM002";
    private final String skuNotInDatabase = "DM003";
    private final String name = "Paper";
    private final String description = "Printing paper";
    private final int quantity = 1;
    private final BigDecimal cost = BigDecimal.valueOf(1.11);
    private final String deleteComments = "Discontinued";

    private ProductDao productDao;

    @Mock
    private DynamoDBMapper mapper;

    @BeforeEach
    public void setup() {
        initMocks(this);
        productDao = new ProductDao(mapper);
    }

    @Test
    public void getActiveProduct_withProductInDatabase_returnsProduct() {
        Product product = Product.builder()
                .withCompanyName(companyName)
                .withName(name)
                .withSku(activeSkuOne)
                .withIsActive(true)
                .withCost(cost)
                .withQuantity(quantity)
                .withDescription(description)
                .build();

        when(mapper.load(Product.class, companyName, activeSkuOne)).thenReturn(product);

        Product productReturned = productDao.getActiveProduct(companyName, activeSkuOne);

        assertEquals(companyName, productReturned.getCompanyName(),
                "Expected company name to be " + companyName);
        assertEquals(activeSkuOne, productReturned.getSku(),
                "Expected sku to be " + activeSkuOne);
        assertEquals(name, productReturned.getName(),
                "Expected name to be " + name);
        assertEquals(cost, productReturned.getCost(), "Expected cost to be " + cost);
        assertEquals(quantity, productReturned.getQuantity(),
                "Expected quantity to be " + quantity);
        assertEquals(description, productReturned.getDescription(),
                "Expected description to be " + description);
        assertEquals(true, productReturned.isActive(),
                "Expected is Active field to be true");
    }

    @Test
    public void getActiveProduct_withProductNotInDatabase_throwsProductNotFoundException() {
        Product product = Product.builder()
                .withCompanyName(companyName)
                .withName(name)
                .withSku(inactiveSkuOne)
                .withIsActive(false)
                .withCost(cost)
                .withQuantity(quantity)
                .withDescription(description)
                .withDeleteComment(deleteComments)
                .build();

        when(mapper.load(Product.class, companyName, activeSkuOne)).thenReturn(product);
        assertThrows(ProductNotFoundException.class, () -> productDao.getActiveProduct(companyName, activeSkuOne));
    }

    @Test
    public void getActiveProduct_withInActiveProductNotInDatabase_throwsProductNotFoundException() {
        when(mapper.load(Product.class, companyName, activeSkuOne)).thenReturn(null);
        assertThrows(ProductNotFoundException.class, () -> productDao.getActiveProduct(companyName, activeSkuOne));
    }

    @Test
    public void getInactiveProduct_withProductInDatabase_returnsProduct() {
        Product product = Product.builder()
                .withCompanyName(companyName)
                .withName(name)
                .withSku(inactiveSkuOne)
                .withIsActive(false)
                .withCost(cost)
                .withQuantity(quantity)
                .withDescription(description)
                .withDeleteComment(deleteComments)
                .build();

        when(mapper.load(Product.class, companyName, inactiveSkuOne)).thenReturn(product);

        Product productReturned = productDao.getInActiveProduct(companyName, inactiveSkuOne);

        assertEquals(companyName, productReturned.getCompanyName(),
                "Expected company name to be " + companyName);
        assertEquals(inactiveSkuOne, productReturned.getSku(),
                "Expected sku to be " + inactiveSkuOne);
        assertEquals(name, productReturned.getName(),
                "Expected name to be " + name);
        assertEquals(cost, productReturned.getCost(), "Expected cost to be " + cost);
        assertEquals(quantity, productReturned.getQuantity(),
                "Expected quantity to be " + quantity);
        assertEquals(description, productReturned.getDescription(),
                "Expected description to be " + description);
        assertEquals(false, productReturned.isActive(),
                "Expected is Active field to be false");
        assertEquals(deleteComments, productReturned.getDeleteComment(),
                "Expected delete comments to be false" + deleteComments);
    }

    @Test
    public void getInactiveProduct_withProductNotInDatabase_throwsProductNotFoundException() {

        when(mapper.load(Product.class, companyName, inactiveSkuOne)).thenReturn(null);
        assertThrows(ProductNotFoundException.class, () -> productDao.getInActiveProduct(companyName, inactiveSkuOne));
    }

    @Test
    public void getInactiveProduct_withActiveProductNotInDatabase_throwsProductNotFoundException() {
        Product product = Product.builder()
                .withCompanyName(companyName)
                .withName(name)
                .withSku(activeSkuOne)
                .withIsActive(true)
                .withCost(cost)
                .withQuantity(quantity)
                .withDescription(description)
                .build();
        when(mapper.load(Product.class, companyName, inactiveSkuOne)).thenReturn(product);
        assertThrows(ProductNotFoundException.class, () -> productDao.getInActiveProduct(companyName, inactiveSkuOne));
    }


    @Test
    public void getProductActiveAndInactive_withProductInDatabase_returnsProduct() {
        Product product = Product.builder()
                .withCompanyName(companyName)
                .withName(name)
                .withSku(inactiveSkuOne)
                .withIsActive(false)
                .withCost(cost)
                .withQuantity(quantity)
                .withDescription(description)
                .withDeleteComment(deleteComments)
                .build();

        when(mapper.load(Product.class, companyName, inactiveSkuOne)).thenReturn(product);

        Product productReturned = productDao.getProductActiveAndInactive(companyName, inactiveSkuOne);

        assertEquals(companyName, productReturned.getCompanyName(),
                "Expected company name to be " + companyName);
        assertEquals(inactiveSkuOne, productReturned.getSku(),
                "Expected sku to be " + inactiveSkuOne);
        assertEquals(name, productReturned.getName(),
                "Expected name to be " + name);
        assertEquals(cost, productReturned.getCost(), "Expected cost to be " + cost);
        assertEquals(quantity, productReturned.getQuantity(),
                "Expected quantity to be " + quantity);
        assertEquals(description, productReturned.getDescription(),
                "Expected description to be " + description);
        assertEquals(false, productReturned.isActive(),
                "Expected is Active field to be false");
        assertEquals(deleteComments, productReturned.getDeleteComment(),
                "Expected delete comments to be false" + deleteComments);
    }

    @Test
    public void getProductActiveAndInactive_withProductNotInDatabase_throwsProductNotFoundException() {

        when(mapper.load(Product.class, companyName, inactiveSkuOne)).thenReturn(null);
        assertThrows(ProductNotFoundException.class, () -> productDao.getProductActiveAndInactive(companyName, inactiveSkuOne));
    }

    @Test
    public void getAllActiveProducts_companyHasTwoActiveProducts_returnsTwoActiveProducts() {
        Product firstProduct = Product.builder()
                .withCompanyName(companyName)
                .withSku(activeSkuOne)
                .withName(name)
                .withDescription(description)
                .withQuantity(quantity)
                .withCost(cost)
                .withIsActive(true)
                .build();

        Product secondProduct = Product.builder()
                .withCompanyName(companyName)
                .withSku(activeSkuTwo)
                .withName(name)
                .withDescription(description)
                .withQuantity(quantity)
                .withCost(cost)
                .withIsActive(true)
                .build();

        List<Product> productList = new ArrayList<>();
        productList.add(firstProduct);
        productList.add(secondProduct);

        when(mapper.query(eq(Product.class), any(DynamoDBQueryExpression.class)))
                .thenReturn(mock(PaginatedQueryList.class, withSettings().defaultAnswer(new ForwardsInvocations(productList))));

        List<Product> returnedProductList = productDao.getAllActiveProducts(companyName);
        assertEquals(2, returnedProductList.size(), "Expected size to be 2");
    }

    @Test
    public void getAllActiveProducts_companyHasZeroActiveProducts_returnsEmptyList() {

        List<Product> productList = new ArrayList<>();

        when(mapper.query(eq(Product.class), any(DynamoDBQueryExpression.class)))
                .thenReturn(mock(PaginatedQueryList.class, withSettings().defaultAnswer(new ForwardsInvocations(productList))));

        List<Product> returnedProductList = productDao.getAllActiveProducts(companyName);
        assertEquals(0, returnedProductList.size(), "Expected List to be empty");
    }

    @Test
    public void getAllInactiveProducts_companyHasOneInactiveProducts_returnsOneInactiveProducts() {
        Product firstProduct = Product.builder()
                .withCompanyName(companyName)
                .withSku(inactiveSkuOne)
                .withName(name)
                .withDescription(description)
                .withQuantity(quantity)
                .withCost(cost)
                .withIsActive(false)
                .withDeleteComment(deleteComments)
                .build();

        List<Product> productList = new ArrayList<>();
        productList.add(firstProduct);

        when(mapper.query(eq(Product.class), any(DynamoDBQueryExpression.class)))
                .thenReturn(mock(PaginatedQueryList.class, withSettings().defaultAnswer(new ForwardsInvocations(productList))));

        List<Product> returnedProductList = productDao.getAllInactiveProducts(companyName);
        assertEquals(1, returnedProductList.size(), "Expected size to be 1");
    }

    @Test
    public void getAllInactiveProducts_companyHasZeroInactiveProducts_returnsEmptyList() {

        List<Product> productList = new ArrayList<>();

        when(mapper.query(eq(Product.class), any(DynamoDBQueryExpression.class)))
                .thenReturn(mock(PaginatedQueryList.class, withSettings().defaultAnswer(new ForwardsInvocations(productList))));

        List<Product> returnedProductList = productDao.getAllInactiveProducts(companyName);
        assertEquals(0, returnedProductList.size(), "Expected List to be empty");
    }

    @Test
    public void saveProduct_validProduct_returnsProduct() {
        Product product = Product.builder()
                .withCompanyName(companyName)
                .withName(name)
                .withSku(activeSkuOne)
                .withIsActive(true)
                .withCost(cost)
                .withQuantity(quantity)
                .withDescription(description)
                .build();

        Product productReturned = productDao.saveProduct(product);

        assertEquals(companyName, productReturned.getCompanyName(),
                "Expected company name to be " + companyName);
        assertEquals(activeSkuOne, productReturned.getSku(),
                "Expected sku to be " + activeSkuOne);
        assertEquals(name, productReturned.getName(),
                "Expected name to be " + name);
        assertEquals(cost, productReturned.getCost(), "Expected cost to be " + cost);
        assertEquals(quantity, productReturned.getQuantity(),
                "Expected quantity to be " + quantity);
        assertEquals(description, productReturned.getDescription(),
                "Expected description to be " + description);
        assertEquals(true, productReturned.isActive(),
                "Expected is Active field to be true");
    }


}
