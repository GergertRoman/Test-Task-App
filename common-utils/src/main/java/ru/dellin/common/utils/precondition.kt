package ru.dellin.common.utils

fun checkException(value: Boolean, exception: Exception) {
  if (!value) {
    throw exception
  }
}
