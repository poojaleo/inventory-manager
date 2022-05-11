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
public final class GetAllInactiveProductsActivity_Factory
    implements Factory<GetAllInactiveProductsActivity> {
  private final Provider<ProductDao> productDaoProvider;

  private final Provider<ProductModelConverter> productModelConverterProvider;

  public GetAllInactiveProductsActivity_Factory(
      Provider<ProductDao> productDaoProvider,
      Provider<ProductModelConverter> productModelConverterProvider) {
    this.productDaoProvider = productDaoProvider;
    this.productModelConverterProvider = productModelConverterProvider;
  }

  @Override
  public GetAllInactiveProductsActivity get() {
    return new GetAllInactiveProductsActivity(
        productDaoProvider.get(), productModelConverterProvider.get());
  }

  public static GetAllInactiveProductsActivity_Factory create(
      Provider<ProductDao> productDaoProvider,
      Provider<ProductModelConverter> productModelConverterProvider) {
    return new GetAllInactiveProductsActivity_Factory(
        productDaoProvider, productModelConverterProvider);
  }
}
