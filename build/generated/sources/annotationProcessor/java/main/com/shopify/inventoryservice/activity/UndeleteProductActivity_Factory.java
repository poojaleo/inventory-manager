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
public final class UndeleteProductActivity_Factory implements Factory<UndeleteProductActivity> {
  private final Provider<ProductDao> productDaoProvider;

  private final Provider<ProductModelConverter> productModelConverterProvider;

  public UndeleteProductActivity_Factory(
      Provider<ProductDao> productDaoProvider,
      Provider<ProductModelConverter> productModelConverterProvider) {
    this.productDaoProvider = productDaoProvider;
    this.productModelConverterProvider = productModelConverterProvider;
  }

  @Override
  public UndeleteProductActivity get() {
    return new UndeleteProductActivity(
        productDaoProvider.get(), productModelConverterProvider.get());
  }

  public static UndeleteProductActivity_Factory create(
      Provider<ProductDao> productDaoProvider,
      Provider<ProductModelConverter> productModelConverterProvider) {
    return new UndeleteProductActivity_Factory(productDaoProvider, productModelConverterProvider);
  }
}
