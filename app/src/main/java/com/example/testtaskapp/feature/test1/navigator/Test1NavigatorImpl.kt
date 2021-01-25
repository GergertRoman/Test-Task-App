package com.example.testtaskapp.feature.test1.navigator

import androidx.navigation.NavDirections
import com.example.testtaskapp.R
import ru.dellin.common.navigation.AppNavigator

class Test1NavigatorImpl : AppNavigator(), Test1Navigator {

  override fun openTest2() {
    globalHandler?.open(R.id.action_testFragment1_to_testFragment2)
  }

  override fun openTest2(navDirections: NavDirections) {
    globalHandler?.open(navDirections)
  }
}