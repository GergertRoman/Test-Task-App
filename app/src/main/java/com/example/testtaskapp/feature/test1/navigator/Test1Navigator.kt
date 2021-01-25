package com.example.testtaskapp.feature.test1.navigator

import androidx.navigation.NavDirections

interface Test1Navigator {
  fun openTest2()
  fun openTest2(navDirections: NavDirections)
}