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
public final class UpdateShipmentActivity_Factory implements Factory<UpdateShipmentActivity> {
  private final Provider<ShipmentDao> shipmentDaoProvider;

  private final Provider<ShipmentModelConverter> shipmentModelConverterProvider;

  public UpdateShipmentActivity_Factory(
      Provider<ShipmentDao> shipmentDaoProvider,
      Provider<ShipmentModelConverter> shipmentModelConverterProvider) {
    this.shipmentDaoProvider = shipmentDaoProvider;
    this.shipmentModelConverterProvider = shipmentModelConverterProvider;
  }

  @Override
  public UpdateShipmentActivity get() {
    return new UpdateShipmentActivity(
        shipmentDaoProvider.get(), shipmentModelConverterProvider.get());
  }

  public static UpdateShipmentActivity_Factory create(
      Provider<ShipmentDao> shipmentDaoProvider,
      Provider<ShipmentModelConverter> shipmentModelConverterProvider) {
    return new UpdateShipmentActivity_Factory(shipmentDaoProvider, shipmentModelConverterProvider);
  }
}
