package ru.dellin.common.validator

import ru.dellin.core.api.validator.Validator

class TextValidator : Validator<CharSequence> {
  override fun valid(value: CharSequence) = value.isNotBlank()
}
