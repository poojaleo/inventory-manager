package com.shopify.inventoryservice.dependency;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.shopify.inventoryservice.activity.CreateCompanyActivity;
import com.shopify.inventoryservice.activity.CreateProductActivity;
import com.shopify.inventoryservice.activity.CreateShipmentActivity;
import com.shopify.inventoryservice.activity.DeleteProductActivity;
import com.shopify.inventoryservice.activity.GetAllActiveProductsActivity;
import com.shopify.inventoryservice.activity.GetAllInactiveProductsActivity;
import com.shopify.inventoryservice.activity.GetAllShipmentActivity;
import com.shopify.inventoryservice.activity.GetCompanyActivity;
import com.shopify.inventoryservice.activity.GetProductActivity;
import com.shopify.inventoryservice.activity.GetShipmentActivity;
import com.shopify.inventoryservice.activity.UndeleteProductActivity;
import com.shopify.inventoryservice.activity.UpdateProductActivity;
import com.shopify.inventoryservice.activity.UpdateShipmentActivity;
import com.shopify.inventoryservice.converters.CompanyModelConverter;
import com.shopify.inventoryservice.converters.ProductModelConverter;
import com.shopify.inventoryservice.converters.ShipmentModelConverter;
import com.shopify.inventoryservice.dynamodb.CompanyDao;
import com.shopify.inventoryservice.dynamodb.ProductDao;
import com.shopify.inventoryservice.dynamodb.ShipmentDao;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerServiceComponent implements ServiceComponent {
  private Provider<DynamoDBMapper> provideDynamoDBMapperProvider;

  private DaggerServiceComponent(Builder builder) {
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static ServiceComponent create() {
    return new Builder().build();
  }

  private CompanyDao getCompanyDao() {
    return new CompanyDao(provideDynamoDBMapperProvider.get());
  }

  private ProductDao getProductDao() {
    return new ProductDao(provideDynamoDBMapperProvider.get());
  }

  private ShipmentDao getShipmentDao() {
    return new ShipmentDao(provideDynamoDBMapperProvider.get());
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.provideDynamoDBMapperProvider =
        DoubleCheck.provider(DaoModule_ProvideDynamoDBMapperFactory.create(builder.daoModule));
  }

  @Override
  public CreateCompanyActivity provideCreateCompanyActivity() {
    return new CreateCompanyActivity(getCompanyDao(), new CompanyModelConverter());
  }

  @Override
  public GetCompanyActivity provideGetCompanyActivity() {
    return new GetCompanyActivity(getCompanyDao(), new CompanyModelConverter());
  }

  @Override
  public CreateProductActivity provideCreateProductActivity() {
    return new CreateProductActivity(getProductDao(), new ProductModelConverter());
  }

  @Override
  public GetProductActivity provideGetProductActivity() {
    return new GetProductActivity(getProductDao(), new ProductModelConverter());
  }

  @Override
  public GetAllActiveProductsActivity provideGetAllActiveProductActivity() {
    return new GetAllActiveProductsActivity(getProductDao(), new ProductModelConverter());
  }

  @Override
  public GetAllInactiveProductsActivity provideGetAllInactiveProductActivity() {
    return new GetAllInactiveProductsActivity(getProductDao(), new ProductModelConverter());
  }

  @Override
  public UpdateProductActivity provideUpdateProductActivity() {
    return new UpdateProductActivity(getProductDao(), new ProductModelConverter());
  }

  @Override
  public DeleteProductActivity provideDeleteProductActivity() {
    return new DeleteProductActivity(getProductDao(), new ProductModelConverter());
  }

  @Override
  public UndeleteProductActivity provideUndeleteProductActivity() {
    return new UndeleteProductActivity(getProductDao(), new ProductModelConverter());
  }

  @Override
  public CreateShipmentActivity provideCreateShipmentActivity() {
    return new CreateShipmentActivity(
        getShipmentDao(), new ShipmentModelConverter(), getProductDao());
  }

  @Override
  public GetAllShipmentActivity provideGetAllShipmentActivity() {
    return new GetAllShipmentActivity(getShipmentDao(), new ShipmentModelConverter());
  }

  @Override
  public GetShipmentActivity provideGetShipmentActivity() {
    return new GetShipmentActivity(getShipmentDao(), new ShipmentModelConverter());
  }

  @Override
  public UpdateShipmentActivity provideUpdateShipmentActivity() {
    return new UpdateShipmentActivity(getShipmentDao(), new ShipmentModelConverter());
  }

  public static final class Builder {
    private DaoModule daoModule;

    private Builder() {}

    public ServiceComponent build() {
      if (daoModule == null) {
        this.daoModule = new DaoModule();
      }
      return new DaggerServiceComponent(this);
    }

    public Builder daoModule(DaoModule daoModule) {
      this.daoModule = Preconditions.checkNotNull(daoModule);
      return this;
    }
  }
}
