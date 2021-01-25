package com.example.testtaskapp.common.presentation.mvvm

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.dellin.common.rx.extension.rxPublishSubj
import ru.dellin.core.api.error.ViewModelErrorHandler

abstract class BaseViewModel : ViewModel(), LifecycleObserver, ViewModelErrorHandler {
  protected open val subscriptions = CompositeDisposable()

  private val _forceLogout = rxPublishSubj<Boolean>()
  private val _errorMessage = rxPublishSubj<String>()

  val forceLogout: Observable<Boolean> get() = _forceLogout
  val errorMessage: Observable<String> get() = _errorMessage

  override fun onCleared() {
    super.onCleared()
    subscriptions.clear()
  }

  override fun forceLogout() = _forceLogout.onNext(true)

  override fun errorMessage(text: String) = _errorMessage.onNext(text)

  fun clearSubscriptions() = subscriptions.clear()

  infix fun CompositeDisposable.add(disposable: Disposable) {
    add(disposable)
  }
}