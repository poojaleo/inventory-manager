package com.shopify.inventoryservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import dagger.internal.Factory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CompanyDao_Factory implements Factory<CompanyDao> {
  private final Provider<DynamoDBMapper> mapperProvider;

  public CompanyDao_Factory(Provider<DynamoDBMapper> mapperProvider) {
    this.mapperProvider = mapperProvider;
  }

  @Override
  public CompanyDao get() {
    return new CompanyDao(mapperProvider.get());
  }

  public static CompanyDao_Factory create(Provider<DynamoDBMapper> mapperProvider) {
    return new CompanyDao_Factory(mapperProvider);
  }
}
