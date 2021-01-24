package ru.dellin.core.api.validator

interface Validator<T> {
  fun valid(value: T): Boolean
}
