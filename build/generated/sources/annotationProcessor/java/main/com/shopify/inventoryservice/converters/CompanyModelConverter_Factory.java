package com.shopify.inventoryservice.converters;

import dagger.internal.Factory;
import javax.annotation.processing.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CompanyModelConverter_Factory implements Factory<CompanyModelConverter> {
  private static final CompanyModelConverter_Factory INSTANCE = new CompanyModelConverter_Factory();

  @Override
  public CompanyModelConverter get() {
    return new CompanyModelConverter();
  }

  public static CompanyModelConverter_Factory create() {
    return INSTANCE;
  }
}
