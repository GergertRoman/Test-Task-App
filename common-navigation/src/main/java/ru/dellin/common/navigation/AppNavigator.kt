package ru.dellin.common.navigation

import ru.dellin.core.api.navigator.Navigator
import ru.dellin.core.api.navigator.NavigatorHandler

open class AppNavigator : Navigator {
  final override var localHandler: NavigatorHandler? = null
  final override var globalHandler: NavigatorHandler? = null
}
