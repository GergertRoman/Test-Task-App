package ru.dellin.common.validator

import ru.dellin.core.api.validator.Validator

private const val PASSWORD_REGEX =
  "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z0-9!@#$%^&amp*()\\-_\\\\+=;:,./?{|}`~\\[\\]]{7,}$"

class PasswordSymbolValidator : Validator<CharSequence> {

  override fun valid(
    value: CharSequence
  ) = value.isNotEmpty() && value.matches(PASSWORD_REGEX.toRegex())
}
