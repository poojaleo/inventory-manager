package com.shopify.inventoryservice.activity;

import com.shopify.inventoryservice.converters.ShipmentModelConverter;
import com.shopify.inventoryservice.dynamodb.ProductDao;
import com.shopify.inventoryservice.dynamodb.ShipmentDao;
import dagger.internal.Factory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CreateShipmentActivity_Factory implements Factory<CreateShipmentActivity> {
  private final Provider<ShipmentDao> shipmentDaoProvider;

  private final Provider<ShipmentModelConverter> shipmentModelConverterProvider;

  private final Provider<ProductDao> productDaoProvider;

  public CreateShipmentActivity_Factory(
      Provider<ShipmentDao> shipmentDaoProvider,
      Provider<ShipmentModelConverter> shipmentModelConverterProvider,
      Provider<ProductDao> productDaoProvider) {
    this.shipmentDaoProvider = shipmentDaoProvider;
    this.shipmentModelConverterProvider = shipmentModelConverterProvider;
    this.productDaoProvider = productDaoProvider;
  }

  @Override
  public CreateShipmentActivity get() {
    return new CreateShipmentActivity(
        shipmentDaoProvider.get(), shipmentModelConverterProvider.get(), productDaoProvider.get());
  }

  public static CreateShipmentActivity_Factory create(
      Provider<ShipmentDao> shipmentDaoProvider,
      Provider<ShipmentModelConverter> shipmentModelConverterProvider,
      Provider<ProductDao> productDaoProvider) {
    return new CreateShipmentActivity_Factory(
        shipmentDaoProvider, shipmentModelConverterProvider, productDaoProvider);
  }
}
