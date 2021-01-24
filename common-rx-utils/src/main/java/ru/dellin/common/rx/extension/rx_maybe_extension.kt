package ru.dellin.common.rx.extension

import io.reactivex.Maybe
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.dellin.common.rx.utils.DisposableObserverWithErrorMessage
import ru.dellin.common.rx.utils.SimpleDisposableObserver
import ru.dellin.core.api.error.ErrorHandler
import ru.dellin.core.api.error.ViewModelErrorHandler
import ru.dellin.core.api.schedulers.SchedulersProvider

fun <T> Maybe<T>.schedulers(
  provider: SchedulersProvider
) = schedulers(provider.io(), provider.ui())

fun <T> Maybe<T>.schedulers() = schedulers(Schedulers.io(), AndroidSchedulers.mainThread())

fun <T> Maybe<T>.schedulers(
  subscribeOnScheduler: Scheduler,
  observeOnScheduler: Scheduler
): Maybe<T> = compose {
  it.subscribeOn(subscribeOnScheduler)
    .observeOn(observeOnScheduler)
}

fun <T> Maybe<T>.progress(progressFun: (Boolean) -> Unit): Maybe<T> = compose {
  it.doOnSubscribe { progressFun.invoke(true) }
    .doAfterTerminate { progressFun.invoke(false) }
}

fun <T> Maybe<T>.listen(
  onErrorFun: ((Throwable) -> Unit)? = null,
  onCompleteFun: (() -> Unit)? = null,
  onNextFun: ((T) -> Unit)? = null
) = subscribeWith(
  object : SimpleDisposableObserver<T>() {

    override fun onError(e: Throwable) {
      e.printStackTrace()
      onErrorFun?.invoke(e)
    }

    override fun onNext(data: T) {
      onNextFun?.invoke(data)
    }

    override fun onComplete() {
      onCompleteFun?.invoke()
    }
  }
)

fun <T> Maybe<T>.listenWithError(
  viewModelErrorHandler: ViewModelErrorHandler,
  errorHandler: ErrorHandler,
  onErrorFun: (Throwable) -> Boolean = { false },
  onCompleteFun: (() -> Unit)? = null,
  onNextFun: ((T) -> Unit)? = null
) = subscribeWith(
  object : DisposableObserverWithErrorMessage<T>(viewModelErrorHandler, errorHandler) {

    override fun onError(e: Throwable) {
      e.printStackTrace()

      if (!onErrorFun.invoke(e)) {
        super.onError(e)
      }
    }

    override fun onNext(data: T) {
      onNextFun?.invoke(data)
    }

    override fun onComplete() {
      onCompleteFun?.invoke()
    }
  }
)
