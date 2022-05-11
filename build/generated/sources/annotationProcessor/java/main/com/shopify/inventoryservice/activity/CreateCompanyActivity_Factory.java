package com.shopify.inventoryservice.activity;

import com.shopify.inventoryservice.converters.CompanyModelConverter;
import com.shopify.inventoryservice.dynamodb.CompanyDao;
import dagger.internal.Factory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CreateCompanyActivity_Factory implements Factory<CreateCompanyActivity> {
  private final Provider<CompanyDao> companyDaoProvider;

  private final Provider<CompanyModelConverter> companyModelConverterProvider;

  public CreateCompanyActivity_Factory(
      Provider<CompanyDao> companyDaoProvider,
      Provider<CompanyModelConverter> companyModelConverterProvider) {
    this.companyDaoProvider = companyDaoProvider;
    this.companyModelConverterProvider = companyModelConverterProvider;
  }

  @Override
  public CreateCompanyActivity get() {
    return new CreateCompanyActivity(companyDaoProvider.get(), companyModelConverterProvider.get());
  }

  public static CreateCompanyActivity_Factory create(
      Provider<CompanyDao> companyDaoProvider,
      Provider<CompanyModelConverter> companyModelConverterProvider) {
    return new CreateCompanyActivity_Factory(companyDaoProvider, companyModelConverterProvider);
  }
}
