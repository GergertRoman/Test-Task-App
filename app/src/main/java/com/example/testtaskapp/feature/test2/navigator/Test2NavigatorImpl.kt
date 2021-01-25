package com.example.testtaskapp.feature.test2.navigator

import androidx.navigation.NavDirections
import com.example.testtaskapp.R
import ru.dellin.common.navigation.AppNavigator

class Test2NavigatorImpl : AppNavigator(), Test2Navigator {

  override fun openTest1() {
    globalHandler?.open(R.id.action_testFragment2_to_testFragment1)
  }

  override fun openTest1(navDirections: NavDirections) {
    globalHandler?.open(navDirections)
  }
}