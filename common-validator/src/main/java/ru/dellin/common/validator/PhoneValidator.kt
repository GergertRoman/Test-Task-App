package ru.dellin.common.validator

import ru.dellin.core.api.validator.PatternValidator

class PhoneValidator : PatternValidator<CharSequence> {
  override fun valid(value: CharSequence, pattern: String) = if (pattern.isNotEmpty()) {
    value.matches(pattern.toRegex())
  } else {
    value.isNotEmpty()
  }
}
