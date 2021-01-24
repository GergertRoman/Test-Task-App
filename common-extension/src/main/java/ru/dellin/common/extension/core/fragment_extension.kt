package ru.dellin.common.extension.core

import android.content.res.Configuration
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun Fragment.getNavigationResult(key: String = "result") =
  findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(key)

fun Fragment.setNavigationResult(result: String, key: String = "result") {
  findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}

fun Fragment.hideKeyboard() {
  requireActivity().hideKeyboard()
}

fun Fragment.getNightThemeMode() =
  resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

fun Fragment.isNightTheme() = getNightThemeMode() == Configuration.UI_MODE_NIGHT_YES

inline fun <reified T : Any> Fragment.getSystemService() = activity?.getSystemService<T>()
