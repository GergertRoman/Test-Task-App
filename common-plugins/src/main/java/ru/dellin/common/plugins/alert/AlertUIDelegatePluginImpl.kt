package ru.dellin.common.plugins.alert

import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import ru.dellin.common.plugins.context
import ru.dellin.common.utils.DEFAULT_NEW_LINE
import ru.dellin.core.android.view.delegate.UIDelegatePlugin

class AlertUIDelegatePluginImpl(
  @StyleRes private val styleResId: Int
) : UIDelegatePlugin<Fragment>(), AlertUIDelegatePlugin {

  override fun showAlert(
    title: String,
    desc: String,
    okTitle: String,
    cancelTitle: String,
    ok: () -> Unit,
    cancel: () -> Unit
  ) {
    context?.also {
      AlertDialog.Builder(it, styleResId)
        .setTitle(title)
        .setMessage(desc + DEFAULT_NEW_LINE)
        .setNegativeButton(cancelTitle) { dialog, _ ->
          dialog?.dismiss()
          cancel()
        }
        .setPositiveButton(okTitle) { _, _ -> ok() }
        .create()
        .show()
    }
  }
}
