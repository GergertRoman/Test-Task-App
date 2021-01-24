package ru.dellin.core.api.error

interface ErrorHandler {
  fun getErrorMessage(throwable: Throwable?): String
}
