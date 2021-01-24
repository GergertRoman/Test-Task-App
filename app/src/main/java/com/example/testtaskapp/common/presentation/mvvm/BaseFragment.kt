package ru.dellin.b2c.common.presentation.mvvm

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.testtaskapp.R
import com.example.testtaskapp.common.presentation.mvvm.AutoDisposable
import com.jakewharton.rxbinding3.view.clicks
import ru.dellin.common.extension.core.hideKeyboard
import ru.dellin.common.plugins.alert.AlertUIDelegatePlugin
import ru.dellin.common.plugins.alert.AlertUIDelegatePluginImpl
import ru.dellin.common.plugins.loading.LoadingUIDelegatePlugin
import ru.dellin.common.plugins.loading.LoadingUIDelegatePluginImpl
import ru.dellin.core.android.view.delegate.UIDelegatePlugin
import ru.dellin.core.android.view.delegate.UIDelegatePluginEvent

private const val DEFAULT_LAYOUT = R.layout.base_fragment

abstract class BaseFragment(
  private val loadingPlugin: LoadingUIDelegatePluginImpl = LoadingUIDelegatePluginImpl(
    R.id.vgLoading
  )
) : Fragment(),
  LoadingUIDelegatePlugin by loadingPlugin {

  private val plugins = mutableListOf<UIDelegatePlugin<Fragment>>()
  protected val subscriptions = AutoDisposable()
  protected abstract val binding: ViewBinding

  init {
    initUIDelegatePlugins()
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)

    subscriptions.bindTo(this)

    sendUIDelegatePluginEvent(UIDelegatePluginEvent.OnAttach)
  }

  override fun onDetach() {
    super.onDetach()

    sendUIDelegatePluginEvent(UIDelegatePluginEvent.OnDetach)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(DEFAULT_LAYOUT, container, false).apply {
    val userView = binding.root
    userView.clicks().subscribe { activity?.hideKeyboard() }
    findViewById<ViewGroup>(R.id.container).addView(userView)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    sendUIDelegatePluginEvent(UIDelegatePluginEvent.OnViewBound(view))

    subscriptions.clear() // Lifecycle doesn't have onViewCreated state
  }

  override fun onDestroy() {
    super.onDestroy()

    sendUIDelegatePluginEvent(UIDelegatePluginEvent.Release)
  }

  // region Private

  private fun sendUIDelegatePluginEvent(event: UIDelegatePluginEvent) {
    plugins.forEach { it.onUIDelegatePluginEvent(event) }
  }

  private fun initUIDelegatePlugins() { addUIDelegatePlugin(loadingPlugin) }

  private fun addUIDelegatePlugin(plugin: UIDelegatePlugin<Fragment>) {
    with(plugins) {
      plugin.target = this@BaseFragment
      add(plugin)
    }
  }

  // endregion
}
