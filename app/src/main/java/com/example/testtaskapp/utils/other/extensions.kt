package com.example.testtaskapp.utils.other

import io.reactivex.Observable
import com.example.testtaskapp.common.presentation.mvvm.AutoDisposable
import ru.dellin.common.rx.utils.SimpleDisposableObserver

fun <T> Observable<T>.listenWith(
  autoDisposable: AutoDisposable,
  onErrorFun: ((Throwable) -> Unit)? = null,
  onCompleteFun: (() -> Unit)? = null,
  onNextFun: ((T) -> Unit)? = null
) = subscribeWith(
  object : SimpleDisposableObserver<T>() {
    override fun onError(e: Throwable) {
      e.printStackTrace()
      if (autoDisposable.canEmitValue()) {
        e.printStackTrace()
        onErrorFun?.invoke(e)
      }
    }

    override fun onNext(data: T) {
      if (autoDisposable.canEmitValue()) {
        onNextFun?.invoke(data)
      }
    }

    override fun onComplete() {
      if (autoDisposable.canEmitValue()) {
        onCompleteFun?.invoke()
      }
    }
  }
).also { autoDisposable.add(it) }
