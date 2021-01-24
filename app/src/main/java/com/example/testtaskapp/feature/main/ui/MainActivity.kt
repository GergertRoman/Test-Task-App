package com.example.testtaskapp.feature.main.ui

import com.example.testtaskapp.common.presentation.mvvm.BaseActivity
import com.example.testtaskapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.dellin.b2c.utils.lifecycle.viewBinding

@AndroidEntryPoint
class MainActivity : BaseActivity() {
  override val binding by viewBinding(ActivityMainBinding::inflate)
}