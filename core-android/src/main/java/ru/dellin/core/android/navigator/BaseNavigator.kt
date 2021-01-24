package ru.dellin.core.android.navigator

import ru.dellin.core.api.navigator.Navigator
import ru.dellin.core.api.navigator.NavigatorHandler

open class BaseNavigator : Navigator {
  override var localHandler: NavigatorHandler? = null
  override var globalHandler: NavigatorHandler? = null
}
