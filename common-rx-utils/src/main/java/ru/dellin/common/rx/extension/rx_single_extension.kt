package ru.dellin.common.rx.extension

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.dellin.common.rx.utils.DisposableObserverWithErrorMessage
import ru.dellin.common.rx.utils.SimpleDisposableObserver
import ru.dellin.core.api.error.ErrorHandler
import ru.dellin.core.api.error.ViewModelErrorHandler
import ru.dellin.core.api.schedulers.SchedulersProvider

fun <T> Single<T>.schedulers(
  provider: SchedulersProvider
) = schedulers(provider.io(), provider.ui())

fun <T> Single<T>.schedulers() = schedulers(Schedulers.io(), AndroidSchedulers.mainThread())

fun <T> Single<T>.schedulers(
  subscribeOnScheduler: Scheduler,
  observeOnScheduler: Scheduler
): Single<T> = compose {
  it.subscribeOn(subscribeOnScheduler)
    .observeOn(observeOnScheduler)
}

fun <T> Single<T>.progress(progressFun: (Boolean) -> Unit): Single<T> = compose {
  it.doOnSubscribe { progressFun.invoke(true) }
    .doAfterTerminate { progressFun.invoke(false) }
}

fun <T> Single<T>.listen(
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

fun <T> Single<T>.listenWithError(
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
