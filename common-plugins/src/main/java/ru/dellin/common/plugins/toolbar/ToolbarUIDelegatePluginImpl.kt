package ru.dellin.common.plugins.toolbar

import android.view.MenuItem
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import ru.dellin.common.extension.paint.drawable.tint
import ru.dellin.common.extension.primitive.defaultFloat
import ru.dellin.common.plugins.context
import ru.dellin.common.plugins.getColor
import ru.dellin.common.plugins.getDrawable
import ru.dellin.common.plugins.getString
import ru.dellin.common.utils.DEFAULT_FLOAT
import ru.dellin.common.utils.delegate.weakReference
import ru.dellin.core.android.view.delegate.UIDelegatePlugin
import ru.dellin.core.android.view.delegate.UIDelegatePluginEvent
import ru.dellin.core.android.view.delegate.findViewById

class ToolbarUIDelegatePluginImpl(
  @IdRes private val toolbarViewId: Int
) : UIDelegatePlugin<Fragment>(), ToolbarUIDelegatePlugin {
  private var toolbar by weakReference<Toolbar>()
  private var visibleBackButton = false
  private var elevation = DEFAULT_FLOAT

  // region UIDelegatePlugin

  override fun onUIDelegatePluginEvent(event: UIDelegatePluginEvent) {
    super.onUIDelegatePluginEvent(event)

    when (event) {
      is UIDelegatePluginEvent.OnViewBound -> {
        toolbar = event.findViewById(toolbarViewId)
        elevation = defaultFloat { toolbar?.elevation }
      }

      is UIDelegatePluginEvent.Release -> toolbar = null
      else -> { }
    }
  }

  // endregion

  // region ToolbarUIDelegatePlugin

  override fun setToolbarTitle(@StringRes res: Int) {
    val title = if (res != 0) getString(res) else null
    setToolbarTitle(title)
  }

  override fun setToolbarTitle(title: String?) {
    toolbar?.title = title
  }

  override fun setToolbarSubTitle(res: Int) {
    val subTitle = if (res != 0) getString(res) else null
    setToolbarTitle(subTitle)
  }

  override fun setToolbarSubTitle(subTitle: String?) {
    toolbar?.subtitle = subTitle
  }

  override fun setVisibleToolbar(visible: Boolean) {
    toolbar?.isVisible = visible
  }

  override fun setToolbarBackButtonCallback(callbackFun: (() -> Unit)?) {
    if (callbackFun != null) {
      toolbar?.setNavigationOnClickListener { callbackFun.invoke() }
    } else {
      toolbar?.setNavigationOnClickListener(null)
    }
  }

  override fun setVisibleToolbarBackButton(
    visible: Boolean,
    @ColorRes iconTint: Int,
    @DrawableRes icon: Int
  ) {
    if (visible == visibleBackButton) {
      return
    }

    val backIcon = if (visible) getDrawable(icon) else null
    val tintBackIcon = getColor(iconTint)

    tintBackIcon?.also { backIcon?.tint(tintBackIcon) }
    toolbar?.navigationIcon = backIcon

    visibleBackButton = visible
  }

  override fun setMenu(@MenuRes res: Int) {
    toolbar?.inflateMenu(res)
  }

  override fun setMenuItemCallback(menuItemFun: ((MenuItem) -> Unit)?) {
    if (menuItemFun != null) {
      toolbar?.setOnMenuItemClickListener {
        menuItemFun.invoke(it)
        true
      }
    } else {
      toolbar?.setOnMenuItemClickListener(null)
    }
  }

  override fun setVisibleToolbarShadow(visible: Boolean) {
    toolbar?.elevation = if (visible) elevation else DEFAULT_FLOAT
  }

  override fun transparentToolbar() {
    context?.also {
      val color = ContextCompat.getColor(it, android.R.color.transparent)
      toolbar?.setBackgroundColor(color)
    }
  }

  override fun setToolbarColor(@ColorRes res: Int) {
    context?.also {
      toolbar?.setBackgroundColor(getColor(res) ?: 0)
    }
  }

  // endregion
}
