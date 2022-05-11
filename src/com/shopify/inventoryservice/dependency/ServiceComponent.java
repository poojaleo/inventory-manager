package com.shopify.inventoryservice.dependency;

import com.shopify.inventoryservice.activity.*;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {DaoModule.class})
public interface ServiceComponent {
    CreateCompanyActivity provideCreateCompanyActivity();
    GetCompanyActivity provideGetCompanyActivity();
    CreateProductActivity provideCreateProductActivity();
    GetProductActivity provideGetProductActivity();
    GetAllActiveProductsActivity provideGetAllActiveProductActivity();
    GetAllInactiveProductsActivity provideGetAllInactiveProductActivity();
    UpdateProductActivity provideUpdateProductActivity();
    DeleteProductActivity provideDeleteProductActivity();
    UndeleteProductActivity provideUndeleteProductActivity();
    CreateShipmentActivity provideCreateShipmentActivity();
    GetAllShipmentActivity provideGetAllShipmentActivity();
    GetShipmentActivity provideGetShipmentActivity();
    UpdateShipmentActivity provideUpdateShipmentActivity();

}
