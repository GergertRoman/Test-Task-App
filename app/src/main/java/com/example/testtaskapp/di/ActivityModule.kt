package com.example.testtaskapp.di

import androidx.fragment.app.FragmentActivity
import dagger.Module
import dagger.Provides
import ru.dellin.core.api.navigator.NavigatorHandler

@Module
class ActivityModule {

  /*@Provides
  fun provideNavigator(activity: FragmentActivity, containerId: Int): NavigatorHandler {
    return AppNavigatorHandlerImpl(activity.supportFragmentManager, containerId)
  }*/
}