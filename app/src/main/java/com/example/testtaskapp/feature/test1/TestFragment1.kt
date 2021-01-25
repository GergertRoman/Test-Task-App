package com.example.testtaskapp.feature.test1

import android.view.View
import com.example.testtaskapp.databinding.TestFragment1Binding
import com.example.testtaskapp.feature.test1.navigator.Test1Navigator
import dagger.hilt.android.AndroidEntryPoint
import com.example.testtaskapp.common.presentation.mvvm.BaseView
import ru.dellin.b2c.utils.lifecycle.viewBinding
import javax.inject.Inject

@AndroidEntryPoint
class TestFragment1 : BaseView<Test1Navigator>() {
  override val binding by viewBinding(TestFragment1Binding::inflate)

  @Inject
  override lateinit var navigator: Test1Navigator

  override fun onViewBound(view: View) = view.setOnClickListener { navigator.openTest2() }

}