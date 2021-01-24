package ru.dellin.common.validator

import ru.dellin.core.api.validator.Validator

private const val EMAIL_REGEX = "^[a-zA-Z0-9!@#\$%&amp;^_?{|}~():*=,\\\\.\\[ \\]+-]" +
  "+@[a-zA-Z0-9!@#\$%&amp;^_?{|}~():*=,\\\\.\\[ \\]+-]" +
  "+\\.[a-zA-Z0-9!@#\$%&amp;^_?{|}~():*=,\\\\.\\[ \\]+-]+\$"

class EmailValidator : Validator<CharSequence> {

  override fun valid(
    value: CharSequence
  ) = value.isNotEmpty() && value.matches(EMAIL_REGEX.toRegex())
}
