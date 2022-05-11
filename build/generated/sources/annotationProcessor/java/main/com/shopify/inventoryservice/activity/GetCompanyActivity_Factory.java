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
public final class GetCompanyActivity_Factory implements Factory<GetCompanyActivity> {
  private final Provider<CompanyDao> companyDaoProvider;

  private final Provider<CompanyModelConverter> companyModelConverterProvider;

  public GetCompanyActivity_Factory(
      Provider<CompanyDao> companyDaoProvider,
      Provider<CompanyModelConverter> companyModelConverterProvider) {
    this.companyDaoProvider = companyDaoProvider;
    this.companyModelConverterProvider = companyModelConverterProvider;
  }

  @Override
  public GetCompanyActivity get() {
    return new GetCompanyActivity(companyDaoProvider.get(), companyModelConverterProvider.get());
  }

  public static GetCompanyActivity_Factory create(
      Provider<CompanyDao> companyDaoProvider,
      Provider<CompanyModelConverter> companyModelConverterProvider) {
    return new GetCompanyActivity_Factory(companyDaoProvider, companyModelConverterProvider);
  }
}
