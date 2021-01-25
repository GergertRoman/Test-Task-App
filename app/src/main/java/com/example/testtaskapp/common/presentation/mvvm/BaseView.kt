package com.example.testtaskapp.common.presentation.mvvm

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import com.example.testtaskapp.R
import com.example.testtaskapp.utils.other.listenWith
import ru.dellin.common.navigation.AppNavigator
import ru.dellin.common.navigation.jetpack.JetpackNavHandlerImpl

private const val DEFAULT_LOCAL_NAV_HOST = R.id.fcv_nav_host_fragment

abstract class BaseView<N> : BaseFragment() {

  private var isFirstViewAttach = true

  private var backPressCallback: OnBackPressedCallback? = null

  protected abstract var navigator: N

  abstract fun onViewBound(view: View)

  open fun onFirstViewAttached() {}
  open fun getBaseViewModel(): BaseViewModel? {
    return null
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    backPressCallback = requireActivity().onBackPressedDispatcher.addCallback(this) {
      (navigator as AppNavigator).localHandler?.run {
        if (parentStackSize() > 0) {
          back()
        } else {
          requireActivity().moveTaskToBack(false)
        }
      }
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initNavigator()

    initViewModel()

    onViewBound(view)

    if (isFirstViewAttach) {
      onFirstViewAttached()
    }

    isFirstViewAttach = false
  }

  override fun onDestroy() {
    super.onDestroy()
    backPressCallback = null
  }

  // region private

  private fun initNavigator() {
    (navigator as AppNavigator).apply {
      globalHandler = (requireActivity() as BaseActivity).navigator
      val navFragment =
        childFragmentManager.findFragmentById(DEFAULT_LOCAL_NAV_HOST) ?: this@BaseView
      localHandler = JetpackNavHandlerImpl(navFragment)
    }
  }

  private fun initViewModel() {
    getBaseViewModel()?.let { vm ->
      vm.errorMessage.listenWith(subscriptions) {
        //showAlert(desc = it, cancelTitle = DEFAULT_STRING)
      }
      vm.forceLogout.listenWith(subscriptions) {
        (navigator as AppNavigator).globalHandler?.open(R.id.action_testFragment1_to_testFragment2)
      }
    }
  }

  // endregion
}
