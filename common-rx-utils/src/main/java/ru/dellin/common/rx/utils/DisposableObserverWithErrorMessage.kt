package ru.dellin.common.rx.utils

import ru.dellin.core.api.error.ErrorHandler
import ru.dellin.core.api.error.ViewModelErrorHandler
import java.lang.ref.WeakReference

open class DisposableObserverWithErrorMessage<T>(
  viewModelErrorHandler: ViewModelErrorHandler?,
  private val errorHandler: ErrorHandler
) : SimpleDisposableObserver<T>() {
  private val vmHandler = WeakReference(viewModelErrorHandler)

  override fun onError(e: Throwable) {
    val errorMsg = errorHandler.getErrorMessage(e)

    if (errorMsg.isNotEmpty()) {
      if (errorMsg == "Unauthorized") { // TODO: add logic on error code
        vmHandler.get()?.forceLogout()
      } else {
        vmHandler.get()?.errorMessage(errorMsg)
      }
    }

    e.printStackTrace()
  }
}
