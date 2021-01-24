package com.example.testtaskapp.di

import androidx.fragment.app.FragmentActivity
import com.example.testtaskapp.feature.main.ui.MainActivity
import dagger.BindsInstance
import dagger.Subcomponent


@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

  @Subcomponent.Factory
  interface Factory {
    fun create(
      @BindsInstance activity: FragmentActivity,
      @BindsInstance containerId: Int
    ): ActivityComponent
  }

  fun inject(activity: MainActivity)
}