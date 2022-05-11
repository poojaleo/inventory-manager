package com.shopify.inventoryservice.activity;

import com.shopify.inventoryservice.converters.ProductModelConverter;
import com.shopify.inventoryservice.dynamodb.ProductDao;
import dagger.internal.Factory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class UpdateProductActivity_Factory implements Factory<UpdateProductActivity> {
  private final Provider<ProductDao> productDaoProvider;

  private final Provider<ProductModelConverter> productModelConverterProvider;

  public UpdateProductActivity_Factory(
      Provider<ProductDao> productDaoProvider,
      Provider<ProductModelConverter> productModelConverterProvider) {
    this.productDaoProvider = productDaoProvider;
    this.productModelConverterProvider = productModelConverterProvider;
  }

  @Override
  public UpdateProductActivity get() {
    return new UpdateProductActivity(productDaoProvider.get(), productModelConverterProvider.get());
  }

  public static UpdateProductActivity_Factory create(
      Provider<ProductDao> productDaoProvider,
      Provider<ProductModelConverter> productModelConverterProvider) {
    return new UpdateProductActivity_Factory(productDaoProvider, productModelConverterProvider);
  }
}
