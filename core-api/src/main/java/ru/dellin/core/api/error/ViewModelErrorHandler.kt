package ru.dellin.core.api.error

interface ViewModelErrorHandler {
  fun forceLogout()
  fun errorMessage(text: String)
}
