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
public final class CreateProductActivity_Factory implements Factory<CreateProductActivity> {
  private final Provider<ProductDao> productDaoProvider;

  private final Provider<ProductModelConverter> productModelConverterProvider;

  public CreateProductActivity_Factory(
      Provider<ProductDao> productDaoProvider,
      Provider<ProductModelConverter> productModelConverterProvider) {
    this.productDaoProvider = productDaoProvider;
    this.productModelConverterProvider = productModelConverterProvider;
  }

  @Override
  public CreateProductActivity get() {
    return new CreateProductActivity(productDaoProvider.get(), productModelConverterProvider.get());
  }

  public static CreateProductActivity_Factory create(
      Provider<ProductDao> productDaoProvider,
      Provider<ProductModelConverter> productModelConverterProvider) {
    return new CreateProductActivity_Factory(productDaoProvider, productModelConverterProvider);
  }
}
