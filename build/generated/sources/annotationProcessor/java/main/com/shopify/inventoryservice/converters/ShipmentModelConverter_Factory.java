package com.shopify.inventoryservice.converters;

import dagger.internal.Factory;
import javax.annotation.processing.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ShipmentModelConverter_Factory implements Factory<ShipmentModelConverter> {
  private static final ShipmentModelConverter_Factory INSTANCE =
      new ShipmentModelConverter_Factory();

  @Override
  public ShipmentModelConverter get() {
    return new ShipmentModelConverter();
  }

  public static ShipmentModelConverter_Factory create() {
    return INSTANCE;
  }
}
