package com.example.testtaskapp.feature.test1

import android.view.View
import com.example.testtaskapp.databinding.TestFragment1Binding
import com.example.testtaskapp.feature.test1.navigator.Test1Navigator
import dagger.hilt.android.AndroidEntryPoint
import com.example.testtaskapp.common.presentation.mvvm.BaseView
import com.jakewharton.rxbinding3.view.clicks
import ru.dellin.b2c.utils.lifecycle.viewBinding
import javax.inject.Inject

@AndroidEntryPoint
class TestFragment1 : BaseView<Test1Navigator>() {
  override val binding by viewBinding(TestFragment1Binding::inflate)

  @Inject
  override lateinit var navigator: Test1Navigator

  override fun onViewBound(view: View) {
    initAction()
  }

  private fun initAction() {
    binding.textView.clicks().subscribe { navigateFurther() }
  }

  private fun navigateFurther() {
    val navDir = TestFragment1Directions.actionTestFragment1ToTestFragment2(
      "Some screen"
    )
    navigator.openTest2(navDir)
  }
}