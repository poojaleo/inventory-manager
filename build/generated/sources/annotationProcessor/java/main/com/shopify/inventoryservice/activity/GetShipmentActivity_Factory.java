package com.shopify.inventoryservice.activity;

import com.shopify.inventoryservice.converters.ShipmentModelConverter;
import com.shopify.inventoryservice.dynamodb.ShipmentDao;
import dagger.internal.Factory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class GetShipmentActivity_Factory implements Factory<GetShipmentActivity> {
  private final Provider<ShipmentDao> shipmentDaoProvider;

  private final Provider<ShipmentModelConverter> shipmentModelConverterProvider;

  public GetShipmentActivity_Factory(
      Provider<ShipmentDao> shipmentDaoProvider,
      Provider<ShipmentModelConverter> shipmentModelConverterProvider) {
    this.shipmentDaoProvider = shipmentDaoProvider;
    this.shipmentModelConverterProvider = shipmentModelConverterProvider;
  }

  @Override
  public GetShipmentActivity get() {
    return new GetShipmentActivity(shipmentDaoProvider.get(), shipmentModelConverterProvider.get());
  }

  public static GetShipmentActivity_Factory create(
      Provider<ShipmentDao> shipmentDaoProvider,
      Provider<ShipmentModelConverter> shipmentModelConverterProvider) {
    return new GetShipmentActivity_Factory(shipmentDaoProvider, shipmentModelConverterProvider);
  }
}
