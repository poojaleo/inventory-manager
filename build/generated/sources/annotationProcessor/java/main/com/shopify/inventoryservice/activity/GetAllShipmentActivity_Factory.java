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
public final class GetAllShipmentActivity_Factory implements Factory<GetAllShipmentActivity> {
  private final Provider<ShipmentDao> shipmentDaoProvider;

  private final Provider<ShipmentModelConverter> shipmentModelConverterProvider;

  public GetAllShipmentActivity_Factory(
      Provider<ShipmentDao> shipmentDaoProvider,
      Provider<ShipmentModelConverter> shipmentModelConverterProvider) {
    this.shipmentDaoProvider = shipmentDaoProvider;
    this.shipmentModelConverterProvider = shipmentModelConverterProvider;
  }

  @Override
  public GetAllShipmentActivity get() {
    return new GetAllShipmentActivity(
        shipmentDaoProvider.get(), shipmentModelConverterProvider.get());
  }

  public static GetAllShipmentActivity_Factory create(
      Provider<ShipmentDao> shipmentDaoProvider,
      Provider<ShipmentModelConverter> shipmentModelConverterProvider) {
    return new GetAllShipmentActivity_Factory(shipmentDaoProvider, shipmentModelConverterProvider);
  }
}
