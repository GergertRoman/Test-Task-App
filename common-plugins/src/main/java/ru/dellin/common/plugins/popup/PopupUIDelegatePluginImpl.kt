package ru.dellin.common.plugins.popup

import androidx.fragment.app.Fragment
import ru.dellin.core.android.popup.showPopup
import ru.dellin.core.android.view.delegate.UIDelegatePlugin

class PopupUIDelegatePluginImpl : UIDelegatePlugin<Fragment>(), PopupUIDelegatePlugin {

  // region PopupUIDelegatePlugin

  override fun showPopup(msg: String) {
    target?.activity?.showPopup(msg)
  }

  // endregion
}
