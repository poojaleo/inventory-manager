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
public final class GetProductActivity_Factory implements Factory<GetProductActivity> {
  private final Provider<ProductDao> productDaoProvider;

  private final Provider<ProductModelConverter> productModelConverterProvider;

  public GetProductActivity_Factory(
      Provider<ProductDao> productDaoProvider,
      Provider<ProductModelConverter> productModelConverterProvider) {
    this.productDaoProvider = productDaoProvider;
    this.productModelConverterProvider = productModelConverterProvider;
  }

  @Override
  public GetProductActivity get() {
    return new GetProductActivity(productDaoProvider.get(), productModelConverterProvider.get());
  }

  public static GetProductActivity_Factory create(
      Provider<ProductDao> productDaoProvider,
      Provider<ProductModelConverter> productModelConverterProvider) {
    return new GetProductActivity_Factory(productDaoProvider, productModelConverterProvider);
  }
}
