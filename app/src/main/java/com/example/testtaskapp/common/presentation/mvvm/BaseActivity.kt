package com.example.testtaskapp.common.presentation.mvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.testtaskapp.R
import ru.dellin.common.navigation.jetpack.JetpackNavHandlerImpl
import ru.dellin.core.api.navigator.NavigatorHandler

abstract class BaseActivity : AppCompatActivity() {
  protected abstract val binding: ViewBinding

  val navigator: NavigatorHandler? by lazy {
    val fragment = supportFragmentManager.findFragmentById(R.id.navFragment)
    fragment?.let { JetpackNavHandlerImpl(it) }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
  }
}