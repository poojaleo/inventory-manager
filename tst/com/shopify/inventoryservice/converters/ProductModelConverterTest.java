package com.shopify.inventoryservice.converters;

import com.shopify.inventoryservice.dynamodb.table.Product;
import com.shopify.inventoryservice.models.ProductModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductModelConverterTest {
    private final String companyName = "Dunder Mifflin";
    private final String sku = "DM001";
    private final String skuAlreadyExist = "DM002";
    private final String name = "Paper";
    private final String description = "Printing paper";
    private final int quantity = 1;
    private final BigDecimal cost = BigDecimal.valueOf(1.11);

    private ProductModelConverter productModelConverter;

    @BeforeEach
    public void setup() {
        productModelConverter = new ProductModelConverter();
    }

    @Test
    public void toProductModel_withProduct_returnsProductModel() {
        Product product = Product.builder()
                .withCompanyName(companyName)
                .withName(name)
                .withSku(sku)
                .withIsActive(true)
                .withCost(cost)
                .withQuantity(quantity)
                .withDescription(description)
                .build();

        ProductModel productModel = productModelConverter.toProductModel(product);

        assertEquals(companyName, productModel.getCompanyName(),
                "Expected company name to be " + companyName);
        assertEquals(sku, productModel.getSku(),
                "Expected sku to be " + sku);
        assertEquals(name, productModel.getName(),
                "Expected name to be " + name);
        assertEquals(cost, productModel.getCost(), "Expected cost to be " + cost);
        assertEquals(quantity, productModel.getQuantity(),
                "Expected quantity to be " + quantity);
        assertEquals(description, productModel.getDescription(),
                "Expected description to be " + description);
        assertEquals(true, productModel.isActive(),
                "Expected is Active field to be true");
    }
}
