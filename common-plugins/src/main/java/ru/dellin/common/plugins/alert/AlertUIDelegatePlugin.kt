package ru.dellin.common.plugins.alert

import ru.dellin.common.utils.DEFAULT_STRING

interface AlertUIDelegatePlugin {
  fun showAlert(
    title: String = DEFAULT_STRING,
    desc: String,
    okTitle: String = "Ок",
    cancelTitle: String = "Отмена",
    ok: () -> Unit = { },
    cancel: () -> Unit = { }
  )
}
