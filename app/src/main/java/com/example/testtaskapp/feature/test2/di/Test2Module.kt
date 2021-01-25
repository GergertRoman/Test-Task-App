package com.example.testtaskapp.feature.test2.di

import com.example.testtaskapp.feature.test1.navigator.Test1Navigator
import com.example.testtaskapp.feature.test1.navigator.Test1NavigatorImpl
import com.example.testtaskapp.feature.test2.TestFragment2
import com.example.testtaskapp.feature.test2.navigator.Test2Navigator
import com.example.testtaskapp.feature.test2.navigator.Test2NavigatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import ru.dellin.core.api.navigator.NavigatorHandler

@InstallIn(FragmentComponent::class)
@Module
class Test2Module {
  @Provides
  fun provideNavigator(): Test2Navigator = Test2NavigatorImpl()
}