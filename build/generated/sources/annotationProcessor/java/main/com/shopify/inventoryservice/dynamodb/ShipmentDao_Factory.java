package com.shopify.inventoryservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import dagger.internal.Factory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ShipmentDao_Factory implements Factory<ShipmentDao> {
  private final Provider<DynamoDBMapper> mapperProvider;

  public ShipmentDao_Factory(Provider<DynamoDBMapper> mapperProvider) {
    this.mapperProvider = mapperProvider;
  }

  @Override
  public ShipmentDao get() {
    return new ShipmentDao(mapperProvider.get());
  }

  public static ShipmentDao_Factory create(Provider<DynamoDBMapper> mapperProvider) {
    return new ShipmentDao_Factory(mapperProvider);
  }
}
