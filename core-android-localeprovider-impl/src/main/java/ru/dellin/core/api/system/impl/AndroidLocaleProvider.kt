package ru.dellin.core.api.system.impl

import android.content.Context
import ru.dellin.common.extension.core.locale
import ru.dellin.common.utils.DEFAULT_STRING
import ru.dellin.core.api.system.LocaleProvider

class AndroidLocaleProvider(private val context: Context?) : LocaleProvider {
  override val currentIso: String
    get() = context?.locale
      ?.isO3Language ?: DEFAULT_STRING
}
