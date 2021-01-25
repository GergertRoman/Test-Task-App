package com.example.testtaskapp.feature.test2

import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.example.testtaskapp.common.presentation.mvvm.BaseView
import com.example.testtaskapp.databinding.TestFragment2Binding
import com.example.testtaskapp.feature.test2.navigator.Test2Navigator
import dagger.hilt.android.AndroidEntryPoint
import ru.dellin.b2c.utils.lifecycle.viewBinding
import javax.inject.Inject

@AndroidEntryPoint
class TestFragment2 : BaseView<Test2Navigator>() {
  override val binding by viewBinding(TestFragment2Binding::inflate)

  private val args: TestFragment2Args by navArgs()

  @Inject
  override lateinit var navigator: Test2Navigator

  override fun onViewBound(view: View) {
    binding.tvText.text = args.screenTitle
    binding.root.setOnClickListener { navigator.openTest1() }
  }
}