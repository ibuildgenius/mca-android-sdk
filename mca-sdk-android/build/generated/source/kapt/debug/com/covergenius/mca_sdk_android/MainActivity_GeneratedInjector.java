package com.covergenius.mca_sdk_android;

import com.covergenius.mca_sdk_android.presentation.MainActivity;

import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.internal.GeneratedEntryPoint;

@OriginatingElement(
    topLevelClass = MainActivity.class
)
@GeneratedEntryPoint
@InstallIn(ActivityComponent.class)
public interface MainActivity_GeneratedInjector {
  void injectMainActivity(MainActivity mainActivity);
}
