package ru.dellin.common.validator

import ru.dellin.core.api.validator.Validator

private const val MIN_LENGTH_PASSWORD = 7

class PasswordLengthValidator : Validator<CharSequence> {

  override fun valid(
    value: CharSequence
  ) = value.length >= MIN_LENGTH_PASSWORD
}
