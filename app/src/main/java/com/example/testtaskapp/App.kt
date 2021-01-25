package com.example.testtaskapp

import android.app.Application
import com.example.testtaskapp.utils.other.RxErrorHandler
import com.facebook.stetho.BuildConfig
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.plugins.RxJavaPlugins

@HiltAndroidApp
open class App : Application() {

  override fun onCreate() {
    super.onCreate()

    if (BuildConfig.DEBUG) {
      initStetho()
    }

    RxJavaPlugins.setErrorHandler(RxErrorHandler())
  }

  private fun initStetho() {
    Stetho.initializeWithDefaults(this)
  }
}
