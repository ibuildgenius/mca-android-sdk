// Generated by Dagger (https://dagger.dev).
package com.covergenius.mca_sdk_android.data.repo;

import com.covergenius.mca_sdk_android.data.remote.API;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import javax.inject.Provider;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class InitRepoImpl_Factory implements Factory<InitRepoImpl> {
  private final Provider<API> apiProvider;

  public InitRepoImpl_Factory(Provider<API> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public InitRepoImpl get() {
    return newInstance(apiProvider.get());
  }

  public static InitRepoImpl_Factory create(Provider<API> apiProvider) {
    return new InitRepoImpl_Factory(apiProvider);
  }

  public static InitRepoImpl newInstance(API api) {
    return new InitRepoImpl(api);
  }
}