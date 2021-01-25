package com.example.testtaskapp.utils.other

import android.util.Log
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.functions.Consumer
import java.io.IOException
import java.io.InterruptedIOException
import java.net.SocketException

class RxErrorHandler : Consumer<Throwable> {
  override fun accept(e: Throwable?) {
    var error: Throwable? = null
    if (e is UndeliverableException) {
      error = e.cause
    }
    if (e is IOException || e is SocketException) {
      // fine, irrelevant network problem or API that throws on cancellation
      return
    }
    if (e is InterruptedException || e is InterruptedIOException) {
      // fine, some blocking code was interrupted by a dispose call
      return
    }
    if (e is NullPointerException || e is IllegalArgumentException) {
      // that's likely a bug in the application
      Thread.currentThread().uncaughtExceptionHandler?.uncaughtException(Thread.currentThread(), e)
      return
    }
    if (e is IllegalStateException) {
      // that's a bug in RxJava or in a custom operator
      Thread.currentThread().uncaughtExceptionHandler?.uncaughtException(Thread.currentThread(), e)
      return
    }
    Log.w("RxJava error", "Undeliverable exception received, not sure what to do. $error")
  }
}
