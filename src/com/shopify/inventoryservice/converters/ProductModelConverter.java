package com.shopify.inventoryservice.converters;

import com.shopify.inventoryservice.dynamodb.table.Product;
import com.shopify.inventoryservice.models.ProductModel;

import javax.inject.Inject;

public class ProductModelConverter {

    @Inject
    public ProductModelConverter() {
    }

    public ProductModel toProductModel(Product product) {
        ProductModel productModel = ProductModel.builder()
                .withCompanyName(product.getCompanyName())
                .withSku(product.getSku())
                .withName(product.getName())
                .withQuantity(product.getQuantity())
                .withCost(product.getCost())
                .withIsActive(product.isActive())
                .build();

        if(product.getDescription() != null) {
            productModel.setDescription(product.getDescription());
        }

        if(product.getDeleteComment() != null) {
            productModel.setDeleteComment(product.getDeleteComment());
        }

        return productModel;

    }


}
