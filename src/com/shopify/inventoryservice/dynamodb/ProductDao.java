package com.shopify.inventoryservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.shopify.inventoryservice.dynamodb.table.Product;
import com.shopify.inventoryservice.exceptions.ProductNotFoundException;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDao {

    private final DynamoDBMapper mapper;
    public static final String PRODUCT_TABLE_NAME = "Products";

    @Inject
    public ProductDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Retrieves the product specified by the sku and if its active
     * @param sku - sku number of the product
     * @return Product stored in the Products table
     * @throws ProductNotFoundException if product not found
     */

    public Product getActiveProduct(String companyName, String sku) throws ProductNotFoundException {
        Product product = mapper.load(Product.class, companyName, sku);

        if(product == null || !product.isActive()) {
                throw new ProductNotFoundException(String.format("ProductNotFound: No active product found for SKU: %s", sku));
        }

        return product;
    }

    public Product getInActiveProduct(String companyName, String sku) throws ProductNotFoundException {
        Product product = mapper.load(Product.class, companyName, sku);

        if(product == null || product.isActive()) {
            throw new ProductNotFoundException(String.format("ProductNotFound: No inactive product found for SKU: %s", sku));
        }

        return product;
    }

    public Product getProductActiveAndInactive(String companyName, String sku) throws ProductNotFoundException {
        Product product = mapper.load(Product.class, companyName, sku);

        if(product == null) {
            throw new ProductNotFoundException(String.format("ProductNotFound: No product found for SKU: %s", sku));
        }

        return product;
    }

    public List<Product> getAllActiveProducts(String companyName) {
        return getAllProducts(companyName, "1");
    }

    public List<Product> getAllInactiveProducts(String companyName) {
        return getAllProducts(companyName, "0");
    }

    private List<Product> getAllProducts(String companyName, String status) {
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":isActive", new AttributeValue().withS(status));
        valueMap.put(":companyName", new AttributeValue().withS(companyName));
        DynamoDBQueryExpression<Product> queryExpression = new DynamoDBQueryExpression<Product>()
                .withIndexName(Product.IS_ACTIVE_INDEX)
                .withConsistentRead(false)
                .withKeyConditionExpression("isActive = :isActive and companyName = :companyName")
                .withExpressionAttributeValues(valueMap);

        return new ArrayList<>(mapper.query(Product.class, queryExpression));
    }

    public Product saveProduct(Product product) {
        mapper.save(product);
        return product;
    }


   /* private ConditionCheck checkSkuValid(String skuToCheck) {
        HashMap<String, AttributeValue> skuMap = new HashMap<>();
        skuMap.put(":sku", new AttributeValue().withS(skuToCheck));

        return new ConditionCheck()
                .withTableName(PRODUCT_TABLE_NAME)
                .withKey(skuMap)
                .withConditionExpression("sku = :sku");
    }

    private Update reduceInventory(int quantityToReduce) {

    }*/

}
