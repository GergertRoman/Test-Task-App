package com.example.testtaskapp.feature.test1.di

import com.example.testtaskapp.feature.test1.navigator.Test1Navigator
import com.example.testtaskapp.feature.test1.navigator.Test1NavigatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
class Test1Module {
  @Provides
  fun provideNavigator(): Test1Navigator = Test1NavigatorImpl()
}