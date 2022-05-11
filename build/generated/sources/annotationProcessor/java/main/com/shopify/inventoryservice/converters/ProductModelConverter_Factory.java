package com.shopify.inventoryservice.converters;

import dagger.internal.Factory;
import javax.annotation.processing.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ProductModelConverter_Factory implements Factory<ProductModelConverter> {
  private static final ProductModelConverter_Factory INSTANCE = new ProductModelConverter_Factory();

  @Override
  public ProductModelConverter get() {
    return new ProductModelConverter();
  }

  public static ProductModelConverter_Factory create() {
    return INSTANCE;
  }
}
